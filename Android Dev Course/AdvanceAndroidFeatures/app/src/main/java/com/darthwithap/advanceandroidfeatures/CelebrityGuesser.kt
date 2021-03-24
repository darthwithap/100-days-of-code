package com.darthwithap.advanceandroidfeatures

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL

class CelebrityGuesser : AppCompatActivity() {
    val TAG = "Celebrity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_celebrity_guesser)

        val weatherButton = findViewById<Button>(R.id.btn_weather_app)

        weatherButton.setOnClickListener {
            startActivity(Intent(this, Weather::class.java))
        }

//        val task = DownloadTask()
//        val result = try {
//            //task.execute("https://en.wikipedia.org/wiki/The_Greatest_Indian").get()
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//
//        Log.d(TAG, "onCreate: RESULT: \n $result")
    }
}

class DownloadTask : AsyncTask<String, Void, String>() {

    override fun doInBackground(vararg urls: String?): String? {
        var result = ""
        var url : URL
        try {
            url = URL(urls[0])
            val connection = url.openConnection()
            val input = connection.getInputStream()
            val inputReader = InputStreamReader(input)
            var data = inputReader.read()

            while (data != -1) {
                result += data
                data = inputReader.read()
            }
            return result
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

}