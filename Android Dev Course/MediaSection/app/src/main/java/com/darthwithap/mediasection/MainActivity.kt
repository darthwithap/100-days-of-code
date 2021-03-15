package com.darthwithap.mediasection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var bart = true

        val bartImg = findViewById<ImageView>(R.id.bart_img)
        val lisaImg = findViewById<ImageView>(R.id.lisa_img)
        val connect3Button = findViewById<Button>(R.id.connect_3_button)

        bartImg.x = 1000F
        bartImg.animate().translationXBy(-1000F).rotation(3600F).duration = 2000

        bartImg.setOnClickListener {
           if (bart) {
               bartImg.animate().alpha(0F).duration = 1500
               lisaImg.animate().alpha(1F).duration = 1500
               bart = false
           }
            else {
               lisaImg.animate().alpha(0F).duration = 1500
               bartImg.animate().alpha(1F).duration = 1500
               bart = true
           }

            //Other animations
            //animate().translationYby(1000)
            //animate().translationXby(-1000)
            //animate().scaleX(2F).scaleY(0.5F) //float needed
            //animate()rotation(360).alpha(0F) //degrees
        }

        connect3Button.setOnClickListener {
            startActivity(Intent(this, Connect3::class.java))
        }

    }
}