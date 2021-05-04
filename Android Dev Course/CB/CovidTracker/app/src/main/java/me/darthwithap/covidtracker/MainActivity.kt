package me.darthwithap.covidtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    lateinit var stateListAdapater: StateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lvStateWise.addHeaderView(
            LayoutInflater.from(this).inflate(R.layout.item_header, null)
        )

        swiperefresh.setOnRefreshListener {
            fetchResults()
            swiperefresh.isRefreshing = false
        }
        fetchResults()
    }

    private fun fetchResults() {
        GlobalScope.launch(Dispatchers.Main) {
            val response = withContext(Dispatchers.IO) { Client.api.clone().execute() }
            if (response.isSuccessful) {
                val data = Gson().fromJson(response.body?.string(), Response::class.java)
                withContext(Dispatchers.Main) {
                    bindCombinedData(data?.statewise?.get(0))
                    data.statewise?.subList(0, data.statewise.size)?.let { bindStateWiseData(it) }
                }
            }

        }
    }

    private fun bindCombinedData(item: StatewiseItem?) {
        val lastUpdatedTime = item?.lastupdatedtime
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        tvLastUpdated.text = "Last Updated\n${getTimeAgo(sdf.parse(lastUpdatedTime))}"

        tvActive.text = item?.active
        tvConfirmed.text = item?.confirmed
        tvDeceased.text = item?.deaths
        tvRecovered.text = item?.recovered
    }

    private fun bindStateWiseData(subList: List<StatewiseItem?>) {
        stateListAdapater = StateAdapter(subList)
        lvStateWise.adapter = stateListAdapater
    }

    fun getTimeAgo(past: Date): String {
        val now = Date()
        val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
        val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time)

        return when {
            seconds < 60 -> "few seconds ago"
            minutes < 60 -> "$minutes minutes ago"
            hours < 24 -> "$hours hrs ${minutes % 60} mins ago"
            else -> SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(past).toString()
        }
    }

}