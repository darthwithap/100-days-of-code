package com.darthwithap.higherorlower

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var randomNumber = (1..30).random()

        findViewById<Button>(R.id.reset_button).setOnClickListener {
            randomNumber = (1..30).random()
            Toast.makeText(this, "Number Reset", Toast.LENGTH_SHORT).show()
        }

        findViewById<Button>(R.id.button_guess).setOnClickListener {
            val enteredValue: String? = findViewById<EditText>(R.id.edit_text).text.toString()
            if (!enteredValue.isNullOrEmpty()) {
                val enteredVal = enteredValue.toInt()
                if(checkAnswer(enteredVal, randomNumber, this)) randomNumber = (1..30).random()
            }
            else Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkAnswer(guessedVal: Int, randomVal: Int, context: Context) : Boolean {
        if (guessedVal == randomVal) {
            Toast.makeText(context, "Hurray Correct Answer!", Toast.LENGTH_SHORT).show()
            return true
        }
        else if (guessedVal > randomVal) Toast.makeText(context, "Too High", Toast.LENGTH_SHORT).show()
        else Toast.makeText(context, "Too Low", Toast.LENGTH_SHORT).show()

        return false
    }
}