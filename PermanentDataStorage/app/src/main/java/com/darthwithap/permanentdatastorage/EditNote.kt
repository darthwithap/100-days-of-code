package com.darthwithap.permanentdatastorage

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class EditNote : AppCompatActivity() {

    lateinit var note : Note
    lateinit var editNote : EditText
    var index : Int = -1
    var originalText = ""

    companion object {
        val NOTE_OBJECT = "note_passed"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_note)

        note = intent.getSerializableExtra(NOTE_OBJECT) as Note
        originalText = note.note
        index = intent.getIntExtra("index", -1)

        editNote = findViewById(R.id.edit_text_note)
        editNote.setText(originalText)

        findViewById<Button>(R.id.btn_save).setOnClickListener {
            save()
        }
    }

    private fun save() {
        val intent = Intent(this, NotesApp::class.java)
        if (editNote.text.toString() != originalText) {
            note = Note(editNote.text.toString())
        }
        intent.putExtra(NOTE_OBJECT, note)
        intent.putExtra("index", index)
        Toast.makeText(this, "Note saved.", Toast.LENGTH_SHORT).show()
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        save()
    }
}