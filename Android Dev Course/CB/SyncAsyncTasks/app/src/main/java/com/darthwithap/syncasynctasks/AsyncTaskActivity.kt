package com.darthwithap.syncasynctasks

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_async_task.*
import kotlin.math.log

class AsyncTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async_task)

        btnStart.setOnClickListener {
            val countTask = CountTask()
            countTask.execute((1..10).random())
        }

        btnRandom.setOnClickListener {
            tvRandom.text = "Random : ${(20..50).random()}"
        }

        btnAsyncToCoroutines.setOnClickListener {
            startActivity(Intent(this, AsyncToCoroutines::class.java))
        }
    }

    inner class CountTask : AsyncTask<Int, Int, Unit>() {
        override fun doInBackground(vararg ints: Int?) {
            Log.d(TAG, "doInBackground: STARTED")
            val n = ints[0] ?: 5
            for (i in (0..n)) {
                waitSeconds(1)
                publishProgress(n, i)
            }
            Log.d(TAG, "doInBackground: ENDED")
        }

        override fun onProgressUpdate(vararg values: Int?) {
            tvCounter.text = "${values[0]?.minus(values[1]!!)}"
        }

        override fun onPostExecute(result: Unit?) {
            tvRandom.text = "Time's Up!"
        }

        private fun waitSeconds(n: Int) {
            val currentTime = System.currentTimeMillis()
            while (System.currentTimeMillis() < currentTime + n * 1000);
        }
    }

    companion object {
        private const val TAG = "AsyncTaskActivity"
    }
}