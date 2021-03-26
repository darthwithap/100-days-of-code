package com.darthwithap.mapsgeolocation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val everest = findViewById<Button>(R.id.btn_everest)

        everest.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        findViewById<Button>(R.id.btn_user_location_screen).setOnClickListener {
            startActivity(Intent(this, UsersLocation::class.java))
        }

        findViewById<Button>(R.id.btn_hikers_app).setOnClickListener {
            startActivity(Intent(this, HikersWatch::class.java))
        }

        findViewById<Button>(R.id.btn_memorable_places_app).setOnClickListener {
            startActivity(Intent(this, MemorablePlaces::class.java))
        }
    }
}