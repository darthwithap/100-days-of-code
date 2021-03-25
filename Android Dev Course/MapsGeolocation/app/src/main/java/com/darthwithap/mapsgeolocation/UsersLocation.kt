package com.darthwithap.mapsgeolocation

import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import java.util.*
import java.util.jar.Manifest

class UsersLocation : AppCompatActivity(), OnMapReadyCallback {

    lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    lateinit var locationRequest : LocationRequest
    lateinit var locationCallback : LocationCallback
    private lateinit var mMap: GoogleMap
    private lateinit var currLocation : Location
    private lateinit var liveLocationButton : Button
    private lateinit var userLocationButton : Button
    private lateinit var mMarker: Marker
    private val TAG = "UsersLocation"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_location)

        liveLocationButton = findViewById<Button>(R.id.btn_user_live_location)
        userLocationButton = findViewById<Button>(R.id.btn_user_location)

        liveLocationButton.apply {
            visibility = View.INVISIBLE
            isClickable = false
        }
        userLocationButton.apply {
            visibility = View.INVISIBLE
            isClickable = false
        }
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        currLocation = Location("Dummy").apply {
            longitude = 86.8869557
            latitude = 28.0249447
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_user) as SupportMapFragment
        mapFragment.getMapAsync(this@UsersLocation)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    currLocation = location
                    Toast.makeText(this@UsersLocation, "${location.longitude} and ${location.latitude}", Toast.LENGTH_SHORT)
                            .show()
                    updateMap(location)
                }
            }
        }

        liveLocationButton.setOnClickListener {
            createLocationRequest()
        }

        userLocationButton.setOnClickListener {
            getLastLocation()
        }

    }

    private fun createLocationRequest() {
            locationRequest = LocationRequest.create().apply {
                interval = 8000
                fastestInterval = 3000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            startLocationUpdates()
        }

        task.addOnFailureListener {
            if (it is ResolvableApiException) {
               try {
                   it.startResolutionForResult(this@UsersLocation, 1001)
               } catch (e: IntentSender.SendIntentException) {
                   e.printStackTrace()
               }
            }
        }

    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) return
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback,
            Looper.getMainLooper())
    }

    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }


    private fun getLastLocation() {
        val task = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }
        task.addOnSuccessListener {
            currLocation = it
            if (it != null) {
                Toast.makeText(applicationContext, "Last Location : ${it.latitude}, ${it.longitude}", Toast.LENGTH_SHORT).show()
                updateMap(it)
                val geocoder = Geocoder(applicationContext, Locale.getDefault())
                try {
                    val addresses = geocoder.getFromLocation(currLocation.latitude, currLocation.longitude, 1)

                    if (addresses != null && addresses.size>0) {
                        Log.d(TAG, "onLocationResult: Addresses: ${addresses[0].adminArea}")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onMapReady(googleMap : GoogleMap?) {
        liveLocationButton.apply {
            visibility = View.VISIBLE
            isClickable = true
        }
        userLocationButton.apply {
            visibility = View.VISIBLE
            isClickable = true
        }

        if (googleMap != null) {
            mMap = googleMap

            mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            val location = LatLng(currLocation.latitude, currLocation.longitude)
            mMarker = mMap.addMarker(MarkerOptions().position(location).title("Your Current Location!")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 14.0f))
        }
    }

    private fun updateMap(location: Location) {
        mMarker.position = LatLng(location.latitude, location.longitude)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(location.latitude, location.longitude)))
    }

}