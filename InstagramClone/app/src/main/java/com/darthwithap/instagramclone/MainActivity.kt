package com.darthwithap.instagramclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.parse.ParseAnalytics

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ParseAnalytics.trackAppOpenedInBackground(intent)
    }
}