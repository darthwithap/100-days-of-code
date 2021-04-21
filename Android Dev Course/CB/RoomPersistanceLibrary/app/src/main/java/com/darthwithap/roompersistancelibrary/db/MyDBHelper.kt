package com.darthwithap.roompersistancelibrary.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHelper(context: Context) : SQLiteOpenHelper(
    context, "todo_db", null, 1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(TodoTable.CMD_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}
