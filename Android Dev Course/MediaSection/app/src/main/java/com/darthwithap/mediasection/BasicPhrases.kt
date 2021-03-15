package com.darthwithap.mediasection

import android.media.MediaPlayer
import android.media.projection.MediaProjectionManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class BasicPhrases : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_phrases)
    }

    fun playPhrase(view: View) {
        val mediaPlayer = MediaPlayer.create(this, resources.getIdentifier(view.tag.toString(), "raw", packageName))
        mediaPlayer.start()
    }

}