package com.darthwithap.dynamicuilistviewscb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_list_view_performance.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item_course.view.*

class ListViewPerformance : AppCompatActivity() {

    private var courses = Course().getNCourses(30)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_performance)

        lvCourses.adapter = CourseAdapter()

        btnNext.setOnClickListener {
            //startActivity(Intent(this, TeacherList::class.java))
        }
    }

    inner class CourseAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return courses.size
        }

        override fun getItem(pos: Int): Course {
            return courses[pos]
        }

        override fun getItemId(pos: Int): Long {
            return 0
        }

        override fun getView(pos: Int, convertView: View?, parent: ViewGroup?): View? {
            //OPTIMISING LIST VIEW WITH CONVERT VIEWS
            val retView: View?
            if (convertView == null) {
                retView = layoutInflater.inflate(R.layout.list_item_course, parent, false)
            }
            else {
                convertView.tvCourseName.text = courses[pos].name
                convertView.tvTeacherName.text = courses[pos].teacher
                convertView.tvLectures.text = courses[pos].lectures.toString()
                retView = convertView
            }
            return retView
        }

    }

    companion object {
        private val TAG = "ListViewPerformance"
    }
}