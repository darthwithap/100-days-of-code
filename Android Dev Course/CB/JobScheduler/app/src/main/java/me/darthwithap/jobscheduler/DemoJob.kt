package me.darthwithap.jobscheduler

import android.app.job.JobParameters
import android.app.job.JobService
import android.widget.Toast

class DemoJob: JobService() {
    override fun onStopJob(params: JobParameters?): Boolean {
        Toast.makeText(this, "Demo Job Stopped", Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        Toast.makeText(this, "Demo Job Started", Toast.LENGTH_SHORT).show()
        return false
    }
}