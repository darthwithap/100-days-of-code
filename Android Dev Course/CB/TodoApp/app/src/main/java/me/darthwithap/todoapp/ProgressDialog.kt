package me.darthwithap.todoapp

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater

class ProgressDialog(context: Context) : AlertDialog(context) {
    init {
        setView(LayoutInflater.from(context).inflate(R.layout.layout_progress_dialog, null))
    }
}