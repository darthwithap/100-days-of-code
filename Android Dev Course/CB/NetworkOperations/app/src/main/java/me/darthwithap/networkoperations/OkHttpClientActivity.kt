package me.darthwithap.networkoperations

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btnGithubUsers
import kotlinx.android.synthetic.main.activity_main.etQuery
import kotlinx.android.synthetic.main.activity_main.rvGithubUsers
import kotlinx.android.synthetic.main.activity_main.tvData
import kotlinx.android.synthetic.main.activity_ok_http_client.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "OkHttpClient"
class OkHttpClientActivity : AppCompatActivity() {
    private var query = "parth"
    private lateinit var users : ArrayList<GithubUser>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ok_http_client)

        btnGithubUsers.setOnClickListener {
            if (etQuery.text.toString().isNotEmpty()) query = etQuery.text.toString()
            makeNetworkCall("https://api.github.com/search/users?q=$query")
        }

        btnMemeShareApp.setOnClickListener {
            startActivity(Intent(this, MemeShare::class.java))
        }

    }

    private fun makeNetworkCall(url: String) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

       client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@OkHttpClientActivity, "Failed to load data.", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call, response: Response) {
                val result = response.body?.string()
                users = parseJson(result)
                val adapter = GithubUserAdapter(users)
                GlobalScope.launch(Dispatchers.Main) {
                    rvGithubUsers.adapter = adapter
                    rvGithubUsers.layoutManager = LinearLayoutManager(this@OkHttpClientActivity, LinearLayoutManager.VERTICAL, true)
                    tvData.text = result
                }
            }

        })
    }

    fun parseJson(s: String?): ArrayList<GithubUser> {
        val userList = arrayListOf<GithubUser>()
        val root = JSONObject(s)
        val items = root.getJSONArray("items")
        for (i in (0 until items.length())) {
            val o = items.getJSONObject(i)
            val user = GithubUser(
                o.getString("login"),
                o.getInt("id"),
                o.getString("html_url"),
                o.getDouble("score"),
                o.getString("avatar_url")
            )
            userList.add(user)
        }
        return userList
    }
}