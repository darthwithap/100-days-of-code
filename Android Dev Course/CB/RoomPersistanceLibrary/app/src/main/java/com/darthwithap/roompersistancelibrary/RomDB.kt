package com.darthwithap.roompersistancelibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.android.synthetic.main.activity_rom_db.*
import kotlinx.coroutines.*

class RomDB : AppCompatActivity() {
    val db by lazy {
        Room.databaseBuilder(
            this,
            UserDatabase::class.java, "User.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rom_db)

        btnSave.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                db.userDao().insert(
                    User(
                        "Parth",
                        "9844578123",
                        "Delhi",
                        20,
                        1
                    )
                )
            }
        }

        btnFetch.setOnClickListener {
            db.userDao().getAllUsers().observe(this, Observer {
                if (it.isNotEmpty()) {
                    with(it[0]) {
                        tvId.text = id.toString()
                        tvName.text = name
                        tvPhNo.text = phNo
                        tvCity.text = city
                        tvAge.text = age.toString()
                        tvSex.text = if (age == 1) "M" else "F"
                    }
                }
            })

        }
    }
}