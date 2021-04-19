package com.darthwithap.syncasynctasks

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.provider.CalendarContract
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val names = arrayListOf<String>("Parth", "Manav", "Parvi", "Sweera", "Amit", "Anil")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lvNames.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, names)

        btnChangeColor.setOnClickListener {
            //ON THE MAIN UI THREAD
            //waitSeconds(5)
            //Different Thread using Handler
            val handler = Handler()
            handler.postDelayed({
                Toast.makeText(this, "Waited 5 seconds on different thread!", Toast.LENGTH_SHORT).show()
                clBackground.setBackgroundColor(Color.CYAN)
            }, 5000)

        }

        btnAsyncTaskActivity.setOnClickListener {
            startActivity(Intent(this, AsyncTaskActivity::class.java))
        }
    }

    private fun waitSeconds(n: Int) {
        val currentTime = System.currentTimeMillis()
        while (System.currentTimeMillis() < currentTime + n*1000);
    }
}