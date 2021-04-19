package com.darthwithap.dynamicuilistviewscb

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_course.view.*

class CourseRecyclerAdapter() : RecyclerView.Adapter<CourseRecyclerAdapter.CourseViewHolder>() {
    private var courses = ArrayList<Course>()

    constructor(courses: ArrayList<Course>) : this() {
        this.courses = courses
    }

    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTeacherName: TextView = itemView.tvTeacherName
        val tvCourseName: TextView = itemView.tvCourseName
        val tvLectures: TextView = itemView.tvLectures
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val layoutInflater = parent.context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = layoutInflater.inflate(R.layout.list_item_course_card, parent, false)

        return CourseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courses[position]
        holder.tvCourseName.text = course.name
        holder.tvTeacherName.text = course.teacher
        holder.tvLectures.text = course.lectures.toString()
    }

    override fun getItemCount(): Int {
        return courses.size
    }
}