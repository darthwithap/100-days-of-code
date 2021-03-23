package com.darthwithap.advanceandroidfeatures

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.SeekBar

class TimesTableApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_times_table_app)
        val TAG = "TimesTable"

        //TIMER
        Handler(Looper.getMainLooper())
            .postDelayed({
                Log.d(TAG, "onCreate: Hey this is after handler!")
            }, 3000)

        //COUNTDOWN TIMER
        object: CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Log.d(TAG, "onTick: Seconds Left ${(millisUntilFinished/1000)}")
            }

            override fun onFinish() {
                Log.d(TAG, "onFinish: Timer is done!")
            }
        }.start()

        val seekBar = findViewById<SeekBar>(R.id.seek_bar)
        val timesTablesListView = findViewById<ListView>(R.id.times_table_list_view)
        val eggTimerButton = findViewById<Button>(R.id.btn_egg_timer)

        eggTimerButton.setOnClickListener {
            startActivity(Intent(this, EggTimer::class.java))
        }

        seekBar.max = 40
        seekBar.progress = 10

        timesTablesListView.adapter = generateTimesTable(5)

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val min = 2
                val timesTableNumber = if (progress < min) {
                    seekBar?.progress = min
                    min
                } else progress
                timesTablesListView.adapter = generateTimesTable(timesTableNumber)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
    }

    fun generateTimesTable(number: Int): ArrayAdapter<String> {
        val timesTableContent = arrayListOf("The values are as follows")

        for (i in (1..20)) {
            timesTableContent.add((i*number).toString())
        }
        return ArrayAdapter(this@TimesTableApp, android.R.layout.simple_list_item_1, timesTableContent)
    }
}