package com.darthwithap.dynamicuilistviewscb

data class Course(val name: String, val teacher: String, val lectures: Int) {
    constructor() : this("", "", 0)


    val TEACHER_NAMES = listOf("Parth", "Manav", "Shashi", "Mayank", "Pranav", "Harshit")
    val COURSE_NAMES = listOf("Android", "OOPS", "DBMS", "Java", "Python", "OS")

    fun getNCourses(n: Int): ArrayList<Course> {
        var courses = ArrayList<Course>()
        for (i in (1..n)) {
            courses.add(
                Course(
                    TEACHER_NAMES[(0..5).random()],
                    COURSE_NAMES[(0..5).random()],
                    10 + (1..10).random()
                )
            )
        }
        return courses
    }
}