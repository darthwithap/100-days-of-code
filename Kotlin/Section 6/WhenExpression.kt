package com.darthwithap.kotlin.section6

import java.math.BigDecimal

enum class Seasons {
    SPRING, SUMMER, FALL, WINTER
}


fun main(args: Array<String>) {

    val timeOfYear = Seasons.WINTER
    val str = when (timeOfYear) {
        Seasons.WINTER -> "Its cold!"
        Seasons.FALL -> "Its cold!"
        Seasons.SPRING -> "Its cold!"
        Seasons.SUMMER -> "Its cold!"
    }
    println(str)

    var something: Any = "String"
    when (something) {
        is String -> println(something.toUpperCase())
        is BigDecimal -> println(something.remainder(BigDecimal(10.2)))
        is Int -> println(something - 22)
        else -> println("No idea!")
    }

    val num = -50
    val num2 = 10

    when {
        num < num2 -> println("$num is less than $num2")
        num > num2 -> println("$num is greater than $num2")
        num == num2 -> println("$num is equal to $num2")
    }
}
