package me.darthwithap.networkoperations

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.security.identity.AccessControlProfileId
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_meme_share.*
import kotlinx.coroutines.delay

class MemeShare : AppCompatActivity() {
    private var imgUrl = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meme_share)
        loadMeme()

        btnShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT,"Hey Check out this amazing meme I found on MemeShare App via Reddit!!\n$imgUrl")
            intent.type = "text/plain"
            val chooser = Intent.createChooser(intent, "Share this meme using...")
            startActivity(chooser)
        }

        btnNext.setOnClickListener {
            loadMeme()
        }
    }

    private fun loadMeme() {
        ivMeme.visibility = View.INVISIBLE
        pb.visibility = View.VISIBLE
        val apiUrl = "https://meme-api.herokuapp.com/gimme"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, apiUrl, null,
            Response.Listener {
                imgUrl = it.getString("url")
                Glide.with(this).load(imgUrl).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pb.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pb.visibility = View.GONE
                        ivMeme.visibility = View.VISIBLE
                        return false
                    }

                }).into(ivMeme)
            }, Response.ErrorListener {
                Thread.sleep(300)
                pb.visibility = View.GONE
                Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show()
            }
        )

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}