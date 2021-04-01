package com.darthwithap.permanentdatastorage

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NotesApp : AppCompatActivity() {

    lateinit var sharedPreferences : SharedPreferences
    lateinit var notesList : ArrayList<Note?>
    lateinit var notesListView : ListView
    var gson = Gson()
    var index = -1
    lateinit var adapter : ArrayAdapter<Note>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_app)
        sharedPreferences = this.getSharedPreferences("com.darthwithap.permanentstorage", Context.MODE_PRIVATE)
        index = intent.getIntExtra("index", -1)

        if (index != -1) {
            updateNote(intent.getStringExtra(EditNote.NOTE_OBJECT))
            saveNotes()
        }
        loadNotes()
        notesListView = findViewById(R.id.list_view_notes)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, notesList)
        notesListView.adapter = adapter

        //itemListView listener
        notesListView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, EditNote::class.java)
            intent.putExtra(EditNote.NOTE_OBJECT, notesList[position])
            intent.putExtra("index", position)
            startActivity(intent)
            finish()
        }
    }

    private fun updateNote(stringExtra: String?) {
        notesList[index] = stringExtra?.let { Note(it) }
    }

    private fun loadNotes() {
        val json = sharedPreferences.getString("notes", null)
        val type = object : TypeToken<ArrayList<Note>>(){}.type
        if (json == null) {
            notesList = ArrayList()
            notesList.add(Note(""))
        }
        else notesList = gson.fromJson(json, type)
    }

    private fun saveNotes() {
        val json = gson.toJson(notesList)
        sharedPreferences.edit().putString("notes", json).apply()
        Toast.makeText(this, "Notes saved.", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.notes_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when (item.itemId) {
            (R.id.menu_add_note) -> {
                val intent = Intent(this, EditNote::class.java)
                intent.putExtra(EditNote.NOTE_OBJECT, notesList[0]?.note)
                intent.putExtra("index", 0)
                startActivity(intent)
                finish()
                true
            }
            else -> false
        }
    }
}