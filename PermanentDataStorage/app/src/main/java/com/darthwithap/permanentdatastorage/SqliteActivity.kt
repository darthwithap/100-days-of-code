package com.darthwithap.permanentdatastorage

import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient

class SqliteActivity : AppCompatActivity() {
    lateinit var mDatabase : SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sqlite)

        mDatabase = this.openOrCreateDatabase("employee", MODE_PRIVATE, null)
        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS employees (name VARCHAR, age INT(3), id INT(5) PRIMARY KEY)")
        mDatabase.execSQL("INSERT INTO employees (name, age) VALUES ('Parth', 22)")
        mDatabase.execSQL("INSERT INTO employees (name, age) VALUES ('Darth', 23)")

        //other queries
        //DELETE FROM users WHERE name = 'Parth'
        //SELECT * FROM users WHERE name LIKE '%a%' LIMIT 1

        val cursor = mDatabase.rawQuery("SELECT * FROM employees", null)
        val nameIndex = cursor.getColumnIndex("name")
        val ageIndex = cursor.getColumnIndex("age")
        val idIndex = cursor.getColumnIndex("id")
        cursor.moveToFirst()

        while (cursor != null) {
            //Log.d(Companion.TAG, "onCreate: ${cursor.getString(idIndex)}. ${cursor.getString(nameIndex)} is ${cursor.getString(ageIndex)}")
            cursor.moveToNext()
        }

        cursor?.close()

        //WEBVIEW
        val webView = findViewById<WebView>(R.id.web_view)
        webView.settings.javaScriptEnabled = true

        webView.webViewClient = WebViewClient()

        webView.loadUrl("https://www.google.com/")
    }

    companion object {
        private const val TAG = "SqliteActivity"
    }
}