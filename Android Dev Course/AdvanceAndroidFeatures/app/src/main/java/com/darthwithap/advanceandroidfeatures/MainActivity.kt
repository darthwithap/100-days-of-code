package com.darthwithap.advanceandroidfeatures

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timesTableButton = findViewById<Button>(R.id.times_table_button)
        timesTableButton.setOnClickListener { startActivity(Intent(this, TimesTableApp::class.java)) }

        val listView = findViewById<ListView>(R.id.list_view)

        val familyMembers = arrayListOf("Parth", "Parth", "Parth", "Parth", "Parth", "Parth")

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, familyMembers)

        listView.adapter = arrayAdapter
        listView.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, familyMembers[position], Toast.LENGTH_SHORT).show()
        }
    }
}