package com.darthwithap.dynamicuilistviewscb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item_fruit.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lvFruits.adapter = ArrayAdapter(
            this,
            R.layout.list_item_fruit,
            R.id.tvFruit,
            arrayOf(
                "Apple",
                "Orange",
                "Papaya",
                "Strawberry",
                "Watermelon",
                "Mango",
                "Kiwi",
                "Guava",
                "Litchi",
                "Tomato",
                "Grapes",
                "Banana",
                "Pineapple"
            )
        )

        btnTeacher.setOnClickListener {
            startActivity(Intent(this, TeacherList::class.java))
        }

        lvFruits.setOnItemClickListener { parent, view, pos, id ->
            Toast.makeText(this, "Johnny ate $pos ${view.tvFruit.text}s", Toast.LENGTH_SHORT).show()
        }
    }
}