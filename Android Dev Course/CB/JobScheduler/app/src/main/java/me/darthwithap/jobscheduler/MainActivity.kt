package me.darthwithap.jobscheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        private const val JOB_ID = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnJobScheduler.setOnClickListener {
            scheduleDemoJob(it)
        }
    }

    private fun scheduleDemoJob(v: View) {
        val component= ComponentName(this, DemoJob::class.java)
        val jobInfo=  JobInfo.Builder(JOB_ID, component)
                .setPeriodic(30 * 60 * 1000,  15 * 60 * 1000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .build()

        val jobScheduler=getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.schedule(jobInfo)
    }
}