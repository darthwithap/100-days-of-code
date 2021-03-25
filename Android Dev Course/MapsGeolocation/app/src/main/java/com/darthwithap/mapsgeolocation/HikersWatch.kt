package com.darthwithap.mapsgeolocation

import android.Manifest
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import java.lang.Exception
import java.util.*

class HikersWatch : AppCompatActivity() {

    lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    lateinit var locationRequest : LocationRequest
    lateinit var locationCallback : LocationCallback
    lateinit var currLocation : Location
    lateinit var longitude : TextView
    lateinit var latitude : TextView
    lateinit var accuracy : TextView
    lateinit var altitude : TextView
    lateinit var address : TextView
    private val TAG = "HikersWatch"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hikers_watch)

        longitude= findViewById(R.id.text_longitude)
        latitude = findViewById(R.id.text_latitude)
        accuracy = findViewById(R.id.text_accuracy)
        altitude = findViewById(R.id.text_altitude)
        address = findViewById(R.id.text_address)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    currLocation = location
                    updateUI()
                }
            }
        }

        createLocationRequest()
    }

    private fun updateUI() {
        longitude.text = "Longitude : ${currLocation.longitude}"
        latitude.text = "Latitude : ${currLocation.latitude}"
        accuracy.text = "Accuracy : ${currLocation.accuracy}"
        altitude.text = "Altitude : ${currLocation.altitude}"


        val geocoder = Geocoder(applicationContext, Locale.getDefault())
        try {
            val addr = geocoder.getFromLocation(currLocation.latitude, currLocation.longitude, 1)

            if (addr != null && addr.size > 0) address.text = "Address : \n" +
                    "${addr[0].adminArea}\n" +
                    "${addr[0].countryName}\n" +
                    "${addr[0].countryCode}"
        } catch (e: Exception) {
            //ignore error
        }
    }

    private fun createLocationRequest() {
        Toast.makeText(this, "createLocationRequestCalled", Toast.LENGTH_SHORT).show()
        locationRequest = LocationRequest.create().apply {
            interval = 2000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            Toast.makeText(this, "Task Successful, startingLocationUpdates", Toast.LENGTH_SHORT).show()
            startLocationUpdates()
        }

        task.addOnFailureListener {
            if (it is ResolvableApiException) {
                try {
                    it.startResolutionForResult(this@HikersWatch, 1)
                } catch (e: IntentSender.SendIntentException) {
                    //ignore error
                }
            }
        }
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }
}