package com.darthwithap.receiversandalarms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class LocaleService : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Locale changed", Toast.LENGTH_SHORT).show()
    }
}