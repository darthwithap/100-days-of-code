package com.darthwithap.receiversandalarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val psr = PowerStateReceiver()
        val iFilter = IntentFilter()
        intent.apply {
            action = Intent.ACTION_POWER_CONNECTED
            action = Intent.ACTION_POWER_DISCONNECTED
        }

        registerReceiver(psr, iFilter)

        btnAlarmActivity.setOnClickListener {
            startActivity(Intent(this, Alarms::class.java))
        }
    }

    inner class PowerStateReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == Intent.ACTION_POWER_DISCONNECTED)
                tvPower.text = "Power Disconnected"
            else if (intent?.action == Intent.ACTION_POWER_CONNECTED)
                tvPower.text = "Power Connected"
        }

    }
}