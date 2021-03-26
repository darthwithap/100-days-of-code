package com.darthwithap.mapsgeolocation

import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.dynamic.IFragmentWrapper
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*
import kotlin.collections.ArrayList

class MemorablePlacesMap : AppCompatActivity(), OnMapReadyCallback {

    var addressList = arrayListOf("My most memorable places!")
    var latitudeList = arrayListOf(0.0)
    var longitudeList = arrayListOf(0.0)

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    lateinit var currLocation : Location
    var addressRequested = ""
    lateinit var mMarker : Marker
    lateinit var mMap : GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memorable_places_map)

        addressRequested = intent.getStringExtra("favAddress")!!
        Log.d(TAG, "onCreate: AddressList $addressList")
        addressList = intent.getStringArrayListExtra("addressList") as ArrayList<String>

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        currLocation = Location("Dummy").apply {
            longitude = 86.8869557
            latitude = 28.0249447
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_memorable_places) as SupportMapFragment
        mapFragment.getMapAsync(this)

        createLocationRequest()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    currLocation = location
                    moveMarker()
                }
            }
        }

    }

    private fun getLastLocation() {
        val task = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 11)
            return
        }

        task.addOnSuccessListener {
            if (it != null) currLocation = it
            moveMarker()
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest.create().apply {
            interval = 6000
            fastestInterval = 3000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            startLocationUpdates()
        }

        task.addOnFailureListener {
            if (it is ResolvableApiException) {
                try {
                    it.startResolutionForResult(this@MemorablePlacesMap, 11)
                } catch (e: IntentSender.SendIntentException) {
                    //ignore error
                }
            }
        }
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION), 11)
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getLastLocation()
        mMarker = mMap.addMarker(MarkerOptions().position(LatLng(currLocation.latitude, currLocation.longitude)).title("Current Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))
        addMarkers()

        mMap.setOnMapLongClickListener {
            val geocoder = Geocoder(applicationContext, Locale.getDefault())
            val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
            val address = addresses[0].getAddressLine(0) + addresses[0]?.subThoroughfare + addresses[0].countryName
            mMap.addMarker(MarkerOptions().title(address).position(LatLng(it.latitude, it.longitude)))
            addressList.add(address)
            latitudeList.add(it.latitude)
            longitudeList.add(it.longitude)
        }
    }

    private fun addMarkers() {
        for (i in (0 until latitudeList.size)) {
            mMap.addMarker(MarkerOptions().title(addressList[i]).position(LatLng(latitudeList[i], longitudeList[i])))
        }
    }

    private fun moveMarker() {
        mMarker.position = LatLng(currLocation.latitude, currLocation.longitude)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(currLocation.latitude, currLocation.longitude), 12.0f))
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
        Log.d(TAG, "onPause: ")
    }

    override fun onBackPressed() {
        val intent = Intent(this, MemorablePlaces::class.java)
        intent.putExtra("addressList", addressList)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val TAG = "MemorablePlacesMap"
    }
}