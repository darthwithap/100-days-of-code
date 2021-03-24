package com.darthwithap.advanceandroidfeatures

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL

class Weather : AppCompatActivity() {
    val TAG = "Weather"
    var city = "London"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        val getWeatherButton = findViewById<Button>(R.id.btn_what_the_weather)
        val cityEditText = findViewById<EditText>(R.id.et_city)

        getWeatherButton.setOnClickListener {
            cityEditText.hideKeyboard()
            Log.d(TAG, "onCreate: Weather Created")
            city = cityEditText.text.toString()
            val task = JSONDownloadTask()
            task.execute("https://api.openweathermap.org/data/2.5/weather?q=$city&appid=b358dceb54c0372fb554426abd86a3ad").get()
        }
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}

class JSONDownloadTask : AsyncTask<String, Void, String>() {

    val TAG = "Weather"

    override fun doInBackground(vararg p0: String?): String? {
        var result = ""

        try {
            val url = URL(p0[0])
            val connection = url.openConnection()
            val inputStream = connection.getInputStream()
            val inputReader = InputStreamReader(inputStream)
            val data = inputReader.read()

            while (data != -1) {
                result += data.toChar()
            }
            return result
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        Log.d(TAG, "onPostExecute: JSON : $result")
        try {
            val json = JSONObject(result)

            val weatherInfo = json.getString("weather") //weather is the key here!
            val arr = JSONArray(weatherInfo)
            for (i in (0 until arr.length())) {
                val jsonPart = arr.getJSONObject(i)

                Log.d(TAG, "onPostExecute: main : ${jsonPart.getString("main")}")
                Log.d(TAG, "onPostExecute: description : ${jsonPart.getString("description")}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}