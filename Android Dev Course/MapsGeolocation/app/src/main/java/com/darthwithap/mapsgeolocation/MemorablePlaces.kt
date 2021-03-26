package com.darthwithap.mapsgeolocation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class MemorablePlaces : AppCompatActivity() {

    companion object {
        private const val TAG = "MemorablePlaces"
    }

    var addressList = arrayListOf("My most memorable places!")

    private lateinit var addressListView : ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memorable_places)

        if (intent.getStringArrayListExtra("addressList") != null) {
            Log.d(TAG, "onCreate: AddressList $addressList")
            addressList = intent.getStringArrayListExtra("addressList") as ArrayList<String>
        }

        addressListView = findViewById(R.id.list_view_memorable_places)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, addressList)
        addressListView.adapter = adapter

        addressListView.setOnItemClickListener{ parent, view, position, id ->
            val intent = Intent(this, MemorablePlacesMap::class.java)
            intent.putExtra("favAddress", addressList[position])
            intent.putExtra("addressList", addressList)
            startActivity(intent)
        }


        findViewById<Button>(R.id.btn_memorable_map_screen).setOnClickListener {
            val intent = Intent(this, MemorablePlacesMap::class.java)
            intent.putExtra("favAddress", "")
            intent.putExtra("addressList", addressList)
            startActivity(intent)
        }
    }

}