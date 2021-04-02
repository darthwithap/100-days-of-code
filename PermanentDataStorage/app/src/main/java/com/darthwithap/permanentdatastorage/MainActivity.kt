package com.darthwithap.permanentdatastorage

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    lateinit var sharedPreferences : SharedPreferences
    var appLanguage = ""
    lateinit var languageText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val TAG = "PermanentDataStorage"
        languageText = findViewById(R.id.text_language)
        sharedPreferences = this.getSharedPreferences("com.darthwithap.permanentdatastorage", Context.MODE_PRIVATE)
        appLanguage = sharedPreferences.getString("language", "")!!
        if (appLanguage == "") showAlert()
        else languageText.text = "App language is $appLanguage"

        //Serializable or Parcelable later...
        sharedPreferences.edit().putString("username", "darthwithap").apply()
        val username = sharedPreferences.getString("username", "")
        Log.d(TAG, "onCreate: Username : $username")

        findViewById<Button>(R.id.btn_notes_app).setOnClickListener {
            startActivity(Intent(this, NotesApp::class.java))
        }

        findViewById<Button>(R.id.btn_sqlite).setOnClickListener {
            startActivity(Intent(this, SqliteActivity::class.java))
        }

        findViewById<Button>(R.id.btn_news_reader).setOnClickListener {
            startActivity(Intent(this, NewsReader::class.java))
        }
    }

    private fun showAlert() {
        AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Choose a language")
            .setMessage("What language do you want to set for the app?")
            .setPositiveButton("English", DialogInterface.OnClickListener { dialog, which ->
                setLanguage("English")
            })
            .setNegativeButton("Japanese", DialogInterface.OnClickListener { dialog, which ->
                setLanguage("Japanese")
            })
            .show()
    }

    private fun setLanguage(language: String) {
        sharedPreferences.edit().putString("language", language).apply()
        languageText.text = "App language is $language"

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        when (item.itemId) {
            (R.id.menu_language) -> {
                showAlert()
                Toast.makeText(this, "Language Changed", Toast.LENGTH_SHORT).show()
            }
            (R.id.menu_settings) -> Toast.makeText(this, "Settings selected!", Toast.LENGTH_SHORT).show()
        }
        return true
    }
}