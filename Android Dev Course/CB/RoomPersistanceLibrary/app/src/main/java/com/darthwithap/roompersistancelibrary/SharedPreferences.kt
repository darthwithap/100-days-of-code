package com.darthwithap.roompersistancelibrary

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_shared_preferences.*

class SharedPreferences : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_preferences)

        sharedPreferences = getPreferences(Context.MODE_PRIVATE)

        val initColor = sharedPreferences.getInt("color", Color.TRANSPARENT)
        saveColor(initColor)

        btnRed.setOnClickListener {
            saveColor(Color.RED)
        }

        btnBlue.setOnClickListener {
            saveColor(Color.BLUE)
        }

        btnGreen.setOnClickListener {
            saveColor(Color.GREEN)
        }

        btnSqliteAct.setOnClickListener {
            startActivity(Intent(this, SQLiteAct::class.java))
        }

    }

    private fun saveColor(color: Int) {
        sharedPreferences.edit().putInt("color", color).apply()
        llBackground.setBackgroundColor(color)
    }
}