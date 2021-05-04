package me.darthwithap.mapsandlocations

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val locationManager by lazy {
        getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onStart() {
        super.onStart()
        requestAccessFineLocation()
        when {
            isFineLocationGranted() -> {
                if (isLocationEnabled()) setupLocationListener()
                else showProviderNotEnabledDialog()
            }
            else -> requestAccessFineLocation()
        }
    }

    private fun isLocationEnabled() = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    private fun showProviderNotEnabledDialog() {
        AlertDialog.Builder(this)
                .setTitle("Enable Location")
                .setMessage("Location needs to be turned on for maps to work")
                .setCancelable(false)
                .setPositiveButton("Enable") { dialog, _ ->
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    Toast.makeText(this, "You have to enable locations.", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }.show()
    }

    private fun requestAccessFineLocation() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 999)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            999 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (isLocationEnabled()) setupLocationListener()
                    else showProviderNotEnabledDialog()
                } else
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isFineLocationGranted() =
            checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    @SuppressLint("MissingPermission")
    private fun setupLocationListener() {
        val providers = locationManager.getProviders(true)
        var location: Location? = null
        for (i in providers.indices.reversed()) {
            location = locationManager.getLastKnownLocation(providers[i])
            if (location != null) break
        }

        location?.let {
            if (::mMap.isInitialized) {
                val currentLocation = LatLng(it.latitude, it.longitude)
                mMap.addMarker(MarkerOptions().position(currentLocation).title("Current Location!"))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}