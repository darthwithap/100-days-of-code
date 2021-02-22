package com.darthwithap.kotlin.challenge1

//declare constants outside of a class on the type
val MY_CONSTANT = 100

fun main(args: Array<String>) {
    println(MY_CONSTANT)
    val car = Car("Blue", "Verna", 2017)
    println(car)
    val car2 = car.copy() //structurally same
    val car3 = car.copy(year = 2018) //apart from copy you get, equals function, haschode function and to string function
    println(car3)
}

// great if class is a state class and is just storing data
// data classes cant be abstract, sealed or inner classes
data class Car (val color: String, val model: String, val year: Int) { //primary constructor can have var or val properties only
    //toString() function inbuilt
}

