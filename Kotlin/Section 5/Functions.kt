package com.darthwithap.kotlin.challenge1

// default return type is Unit
fun main(args: Array<String>) {
    println(multiply(2, 4, "Label"))
    // named args
    println(multiply(label = "The answer is: ", operand1 = 21, operand2 = 2))
    numbers(1, 2, 3, 4, str = "The end")
    number("The beginning", 1, 2, 3, 4)
    val arr = intArrayOf(1, 2, 3, 4, 5)
    val arr2 = intArrayOf(1, 2, 3, 4, 5)
    print(number("Unpacked using spread operator ", *arr, *arr2))
}

//expression body
fun multiply(operand1: Int, operand2: Int, label: String) =
    "$label ${operand1*operand2}"

fun numbers(vararg nums: Int, str: String) {
    for (i in nums) print("$i ")
    println(str)
}

fun number(str: String, vararg nums: Int) {
    print(str)
    for (i in nums) print(" $i ")
    println()
}

//function parameters need to include type