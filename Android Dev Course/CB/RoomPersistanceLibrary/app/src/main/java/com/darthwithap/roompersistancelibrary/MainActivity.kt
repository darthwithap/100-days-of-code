package com.darthwithap.roompersistancelibrary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnWrite.setOnClickListener {
            val dataDirectory = ContextCompat.getDataDir(this)
            val myFile = File(dataDirectory, "file.txt")
            val text = etName.text.toString()

            try {
                val fileOutputStream = FileOutputStream(myFile, true)
                fileOutputStream.write(text.toByteArray())
            } catch (fnfe: FileNotFoundException) {
                Toast.makeText(this, "File not Found!", Toast.LENGTH_SHORT).show()
            } catch (ioe: IOException) {
                Toast.makeText(this, "Error while writing file", Toast.LENGTH_SHORT).show()
            }

            //whole try catch block can be written in just one line in kotlin!
            //myFile.writeText(text)
        }

        btnRead.setOnClickListener {
            val dataDirectory = ContextCompat.getDataDir(this)
            val myFile = File(dataDirectory, "file.txt")

            try {
                val fileInputStream = FileInputStream(myFile)
                val inputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                val stringBuilder = StringBuilder()
                var stringBuffer = bufferedReader.readLine()

                while (stringBuffer!= null) {
                    stringBuilder.append(stringBuffer)
                    stringBuffer = bufferedReader.readLine()
                }

                val text = stringBuilder.toString()
                tvContent.text = text

                //TO READ TEXT JUST USE!!
                //tvContent.text = myFile.readText()

            } catch (fnfe: FileNotFoundException) {
                Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show()
            } catch (ioe: IOException) {
                Toast.makeText(this, "Error while reading file", Toast.LENGTH_SHORT).show()
            }
        }

        btnSharedPrefAct.setOnClickListener {
            startActivity(Intent(this, SharedPreferences::class.java))
        }
    }
}