package com.darthwithap.advanceandroidfeatures

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import java.util.*
import kotlin.properties.Delegates

class EggTimer : AppCompatActivity() {

    lateinit var textTimer : TextView
    lateinit var timer: CountDownTimer
    lateinit var gong : MediaPlayer
    lateinit var ticker : MediaPlayer
    lateinit var goButton : Button
    lateinit var seekBar : SeekBar
    var time = 30
    val TAG = "EggTimer"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_egg_timer)

        seekBar = findViewById<SeekBar>(R.id.seek_bar_timer)
        textTimer = findViewById<TextView>(R.id.text_timer)
        goButton = findViewById<Button>(R.id.btn_go)
        gong = MediaPlayer.create(this, R.raw.gong_sound)
        ticker = MediaPlayer.create(this, R.raw.ticking_sound)
        val brainTrainerButton = findViewById<Button>(R.id.btn_brain_trainer)
        var seekable = true

        updateTimer(time)
        updateTextTimer(time)

        seekBar.max = 300
        seekBar.progress = 30

        brainTrainerButton.setOnClickListener {
            startActivity(Intent(this, BrainTrainer::class.java))
        }

        goButton.setOnClickListener {
            goButton.text = if (seekable) "STOP!"
                            else "GO!"
            if (seekable) startTimer(time)
            else stopTimer()

            seekable = !seekable
            seekBar.isEnabled = seekable
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val min = 5
                time = if (progress < min) {
                    seekBar?.progress = min
                    min
                } else progress
                updateTextTimer(time)
                updateTimer(time)

            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }

    private fun updateTimer(t: Int) {
        var i = t
        timer = object: CountDownTimer((t * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateTextTimer(i--)
            }

            override fun onFinish() {
                updateTextTimer(0)
                gong.seekTo(1700)
                gong.start()
                goButton.performClick()
                timer.cancel()
                seekBar.progress = 30
                time = 30
                updateTimer(30)
                updateTextTimer(time)
                stopTimer()
            }

        }
    }


    private fun startTimer(time: Int) {
        ticker.seekTo((300-time)*1000)
        ticker.start()
        timer.start()
    }

    private fun stopTimer() {
        ticker.pause()
        timer.cancel()
    }

    private fun updateTextTimer(time: Int) {
        textTimer.text = if (time%60 >= 10) "0${time / 60}:${time % 60}"
        else "0${time / 60}:0${time % 60}"
    }
}