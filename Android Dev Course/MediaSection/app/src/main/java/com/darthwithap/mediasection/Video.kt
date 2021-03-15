package com.darthwithap.mediasection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.MediaController
import android.widget.VideoView

class Video : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        val videoView = findViewById<VideoView>(R.id.video_view)
        val audioButton = findViewById<Button>(R.id.audio_button)

        videoView.setVideoPath("android.resource://$packageName/${R.raw.sea_waves}")
        
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)
        videoView.start()

        audioButton.setOnClickListener {
            startActivity(Intent(this, AudioActivity::class.java))
        }
    }
}