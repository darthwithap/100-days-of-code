package com.darthwithap.mediasection

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class AudioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val basicPhrasesButton = findViewById<Button>(R.id.basic_phrases_button)
	//extra space limne

        setContentView(R.layout.activity_audio)
        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        val mediaPlayer = MediaPlayer.create(this, R.raw.seagulls_sound)
        val seekBarText = findViewById<TextView>(R.id.seekbar_text)
        val pauseButton = findViewById<Button>(R.id.pause_button)
        val playButton = findViewById<Button>(R.id.play_button)
        val volumeSeekBar = findViewById<SeekBar>(R.id.volume_seek_bar)
        val scrubSeekBar = findViewById<SeekBar>(R.id.scrub_seekbar)

        scrubSeekBar.max = mediaPlayer.duration

        volumeSeekBar.max = maxVolume
        volumeSeekBar.progress = currentVolume
        basicPhrasesButton.setOnClickListener {
            startActivity(Intent(this, BasicPhrases::class.java))
        }

        playButton.setOnClickListener { mediaPlayer.start() }
        pauseButton.setOnClickListener { mediaPlayer.pause() }

        volumeSeekBar?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBarText.text = progress.toString()
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        scrubSeekBar?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mediaPlayer.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                mediaPlayer.pause()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mediaPlayer.start()
            }

        })

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                scrubSeekBar.progress = mediaPlayer.currentPosition
            }
        }, 0, 200)

    }
}