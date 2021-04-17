package com.darthwithap.dynamicuilistviewscb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_teacher_list.*
import kotlinx.android.synthetic.main.list_item_teacher.view.*

class TeacherList : AppCompatActivity() {

    private var teachers  = Teacher().getRandomTeachers()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_list)

        val teacherAdapter = TeacherAdapter()
        lvTeachers.adapter = teacherAdapter

        btnCoursesActivity.setOnClickListener {
            startActivity(Intent(this, ListViewPerformance::class.java))
        }
    }

    inner class TeacherAdapter : BaseAdapter() {
        override fun getView(pos: Int, convertView: View?, parent: ViewGroup?): View {
            val itemView = layoutInflater.inflate(R.layout.list_item_teacher, parent, false)
            itemView.tvName.text = getItem(pos).name
            itemView.tvSubject.text = getItem(pos).subject
            return itemView
        }

        override fun getItem(pos: Int): Teacher {
            return teachers[pos]
        }

        override fun getItemId(pos: Int): Long {
            return 0
        }

        override fun getCount(): Int {
            return teachers.size
        }

    }
}

