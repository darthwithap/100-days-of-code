package com.darthwithap.permanentdatastorage

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView

class NotesApp : AppCompatActivity() {

    lateinit var sharedPreferences : SharedPreferences
    lateinit var notesList : ArrayList<Note>
    lateinit var notesListView : ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_app)
        sharedPreferences = this.getSharedPreferences("com.darthwithap.permanentstorage", Context.MODE_PRIVATE)

        notesListView = findViewById(R.id.list_view_notes)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, notesList)
        notesListView.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.notes_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when (item.itemId) {
            (R.id.menu_add_note) -> {
                //ADD A NOTE
                true
            }
            else -> false
        }
    }
}