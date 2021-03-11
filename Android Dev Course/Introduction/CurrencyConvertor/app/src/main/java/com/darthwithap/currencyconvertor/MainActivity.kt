package com.darthwithap.currencyconvertor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class  MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inrText = findViewById<EditText>(R.id.inr_amt)
        val usdText = findViewById<TextView>(R.id.usd_amt)

        val convertButton = findViewById<Button>(R.id.convert_button)
        convertButton.setOnClickListener {
            if (inrText.text.isNullOrEmpty()) Toast.makeText(
                this,
                "Invalid input",
                Toast.LENGTH_LONG
            ).show()
            else {
                usdText.text = (inrText.text.toString().toDouble() * 75.56).toString()
            }
        }
    }
}
