package com.darthwithap.mapsgeolocation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class MemorablePlaces : AppCompatActivity() {

    lateinit var addressListView : ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memorable_places)

        addressListView = findViewById(R.id.list_view_memorable_places)
    }

    companion object {
        private const val TAG = "MemorablePlaces"
    }
}