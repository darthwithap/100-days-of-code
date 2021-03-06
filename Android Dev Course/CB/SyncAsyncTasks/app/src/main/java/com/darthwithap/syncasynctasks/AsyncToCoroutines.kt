package com.darthwithap.syncasynctasks

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_async_to_coroutines.*

class AsyncToCoroutines : AppCompatActivity() {
    private lateinit var counterTask: CounterTask
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async_to_coroutines)

        btnStart.setOnClickListener {
            val n = etNumber.text.toString().toInt()
            counterTask = CounterTask()
            counterTask.execute(n)
        }

        btnStop.setOnClickListener {
            counterTask.cancel(false)
        }
    }

    inner class CounterTask : CoroutineAsyncTask <Int, Int, String>() {
        override fun doInBackground(vararg params: Int?): String {
            var count = params[0] ?: 0
            while (count >= 0) {
                if (isCancelled) return "END."
                Thread.sleep(1000)
                publishProgress(count--)
            }
            return "DONE!"
        }

        override fun onProgressUpdate(vararg values: Int?) {
            val progress = values[0] ?: 0
            tvStatus.text = progress.toString()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            tvStatus.text = result
        }

        override fun onCancelled(result: String?) {
            super.onCancelled(result)
            tvStatus.text = result
        }
    }
}