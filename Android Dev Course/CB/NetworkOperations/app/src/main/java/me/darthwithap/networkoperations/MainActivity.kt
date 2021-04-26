package me.darthwithap.networkoperations

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private var query = "parth"
    private lateinit var users : ArrayList<GithubUser>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnGithubUsers.setOnClickListener {
            if (etQuery.text.toString().isNotEmpty()) query = etQuery.text.toString()
            Log.d(TAG, "onCreate: $query")
            NetworkTask().execute("https://api.github.com/search/users?q=$query")
        }

    }

    inner class NetworkTask : CoroutineAsyncTask<String, Unit, String>() {
        override fun doInBackground(vararg params: String?): String {
            try {
                val url = URL(params[0])
                val connection = url.openConnection() as HttpURLConnection
                val inputStream = connection.inputStream
                val sc = Scanner(inputStream)
                sc.useDelimiter("\\A")

                if (sc.hasNext()) {
                    return sc.next()
                }
            } catch (e: MalformedURLException) {}
              catch (ioe: IOException) {}
            return "failed to load"
        }

        override fun onPostExecute(result: String?) {
            users = parseJson(result)
            val adapter = GithubUserAdapter(users)
            rvGithubUsers.adapter = adapter
            rvGithubUsers.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, true)
            tvData.text = result
        }
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