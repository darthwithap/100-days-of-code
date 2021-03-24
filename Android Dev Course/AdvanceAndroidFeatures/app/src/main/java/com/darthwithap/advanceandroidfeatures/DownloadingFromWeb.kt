package com.darthwithap.advanceandroidfeatures

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import java.lang.Exception
import java.net.URL

class DownloadingFromWeb : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_downloading_from_web)

        val image = findViewById<ImageView>(R.id.img_downloaded)
        val celebrityGuesserButton = findViewById<Button>(R.id.btn_celebrity_guesser)
        val downloadButton = findViewById<Button>(R.id.btn_download)

        downloadButton.setOnClickListener {
            val task = ImageDonwnloader()
            try {
                val bitmap = task.execute("https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/9f28ed26066259.5634f12b6e772.jpg ").get()
                image.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        celebrityGuesserButton.setOnClickListener {
            startActivity(Intent(this, CelebrityGuesser::class.java))
        }

    }
}

class ImageDonwnloader : AsyncTask<String, Void, Bitmap>() {

    override fun doInBackground(vararg urls: String?): Bitmap? {
        return try {
            val url = URL(urls[0])
            val connection = url.openConnection()
            connection.connect()
            val input = connection.getInputStream()
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}
