package com.darthwithap.dynamicuilistviewscb

data class Teacher(val name: String, val subject: String) {
    constructor() : this("", "")

    fun getRandomTeachers(): ArrayList<Teacher> {
        val teachers = ArrayList<Teacher>()
        teachers.add(Teacher("Parth", "Android"))
        teachers.add(Teacher("Tanisha", "Java"))
        teachers.add(Teacher("Mayuri", "English"))
        teachers.add(Teacher("Manav", "Accounts"))
        teachers.add(Teacher("Rishab", "Sports"))
        teachers.add(Teacher("Anushka", "Soft Skills"))
        teachers.add(Teacher("Madhav", "Life Skills"))
        teachers.add(Teacher("Yash", "Statistics"))
        teachers.add(Teacher("Aishwarya", "Maths"))
        teachers.add(Teacher("Sarthak", "History"))

        return teachers
    }
}