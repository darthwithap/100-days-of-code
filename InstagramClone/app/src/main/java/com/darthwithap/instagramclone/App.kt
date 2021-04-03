package com.darthwithap.instagramclone

import android.app.Application
import android.util.Log
import com.parse.Parse
import com.parse.ParseACL
import com.parse.ParseObject
import com.parse.ParseUser

class App : Application() {

    companion object {
    private const val TAG = "App"
}
    override fun onCreate() {
        super.onCreate()
        Parse.enableLocalDatastore(this)

        Parse.initialize(
            Parse.Configuration.Builder(applicationContext)
                .applicationId(getString(R.string.aws_application_id))
                .server(getString(R.string.aws_server_url))
                .clientKey(getString(R.string.aws_client_key))
                .build()
        )

        val `object` =ParseObject("Example Object")
        `object`.put("myNumber", "123")
        `object`.put("myString", "parth")
        `object`.saveInBackground {
            if (it == null) Log.d(TAG, "onCreate: Parse Result successfull")
            else Log.d(TAG, "onCreate: Parse Result failed. $it")
        }
        ParseUser.enableAutomaticUser()
        val defaultACL = ParseACL()
        defaultACL.publicReadAccess = true
        defaultACL.publicWriteAccess = true
        ParseACL.setDefaultACL(defaultACL, true)
    }

}