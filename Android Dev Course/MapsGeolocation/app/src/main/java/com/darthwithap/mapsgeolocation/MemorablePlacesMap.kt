package com.darthwithap.mapsgeolocation

import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MemorablePlacesMap : AppCompatActivity(), OnMapReadyCallback {

    var addressList = arrayListOf("My most memorable places!")
    var latitudeList = arrayListOf(0.0)
    var longitudeList = arrayListOf(0.0)

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: OnMapReadyCallback
    lateinit var currLocation : Location
    lateinit var mMap : GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memorable_places_map)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_memorable_places) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        //Add marker to current location - current location Marker
        //SHOW EXISTING MARKERS ON LOCATIONS
    }
}