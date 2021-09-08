package com.darthwithap.whatsappintent

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.text.isDigitsOnly

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var number = "9999999999"

        if (intent.action == Intent.ACTION_PROCESS_TEXT) {
            number = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString()
                .filter { !it.isWhitespace() }
            setResult(RESULT_OK)
        }

        if (isNumberWithoutPlus(number) || isNumberWithPlus(number)) {
            startWhatsApp(number)
        } else {
            Toast.makeText(this, "Please check the number", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun isNumberWithPlus(number: CharSequence): Boolean {
        val num = number.substring(1, number.length)
        return (num.isDigitsOnly() && (num.length in 10..12 step 2))
    }

    private fun isNumberWithoutPlus(number: CharSequence): Boolean {
        return (number.isDigitsOnly() && number.length in 10..12 step 2)
    }


    private fun startWhatsApp(number: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setPackage("com.whatsapp")

        val data = when {
            number[0] == '+' -> {
                number.substring(1)
            }
            number.length == 10 -> {
                "91$number"
            }
            else -> number
        }

        intent.data = Uri.parse("https://wa.me/$data")

        startActivity(intent)
        finish()
    }
}