package com.darthwithap.dynamicuilistviewscb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recycler.*

class RecyclerActivity : AppCompatActivity() {

    val courses = Course().getNCourses(40)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        val courseAdapter = CourseRecyclerAdapter(courses)
        rvCourses.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvCourses.adapter = courseAdapter
    }
}