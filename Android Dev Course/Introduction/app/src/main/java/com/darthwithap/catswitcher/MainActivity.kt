package com.darthwithap.catswitcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var flag = true

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val switchButton: Button = findViewById(R.id.switch_button)
        val catImage: ImageView = findViewById(R.id.cat_image_view)

        switchButton.setOnClickListener {
            if (flag) {
                catImage.setImageResource(R.drawable.cat2)
                flag = false
            }
            else {
                catImage.setImageResource(R.drawable.cat1)
                flag = true
            }
        }
    }
}