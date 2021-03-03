package com.darthwithap.kotlin.section7

import com.darthwithap.kotlin.challenge1.number
import java.lang.StringBuilder

fun main(args: Array<String>) {
    run { println("Inside a lambda") }

    val employees = listOf(Employee("John", "Smith", 2015),
        Employee("Steve", "Smith", 2010),
        Employee("John", "Nash", 2012))
    println(employees.minByOrNull { e -> e.startYear }) //lambda outside parenthesis when it is the last parameter
    println(employees.minByOrNull { it.startYear })
    // member function when only access one property or top level function without parameters
    println(employees.minByOrNull(Employee::startYear))

    run(::topLevel)

    var num =10
    run {
        num += 15
        println(num)
    }
    useParameter(employees, num)

    println(countTo(20))
    println(countToUsingWith(10))
    println(countToUsingApply(5))

    println(findByLastName(employees, "Smith"))

    "Some String".apply outer@ {
        "Another String".apply inner@ {
            println(this@outer.toLowerCase())
            println(toUpperCase())
        }
    }
}

fun topLevel() {
    println("I am a topLevel function")
}

fun useParameter(employees: List<Employee>, num: Int) {
    employees.forEach {
        println(it.firstName)
        println(num)
    }
}

fun findByLastName(employees: List<Employee>, lastName: String) {
    employees.forEach block@{
        if (it.lastName == lastName) {
            println("There is an employee with the last name $lastName")
            return@block // returning from lambda and function only when function taking lambda is in-line function
         }
    }
    println("No there is not an employee with last name $lastName")
}

fun countTo(num: Int): String {
    val numbers = StringBuilder()
    for (i in 1..num) {
        if (i  == num) break
        numbers.append(i)
        numbers.append(", ")
    }
    numbers.append(num)
    return numbers.toString()
}

fun countToUsingWith(num: Int) = with (StringBuilder()) {
        for (i in 1..num){
            if (i == num) break
            append(i)
            append(", ")
        }
        append(num)
        toString()
    }

fun countToUsingApply(num: Int) = StringBuilder().apply {
        for (i in 1..num){
            if (i == num) break
            append(i) // receiver object here is the StringBuilder (in lambda function)
            append(", ")
        }
        append(num)
    }.toString()




class Employee(val firstName: String, val lastName: String, val startYear: Int) {
    override fun toString(): String {
        return "Employee $firstName $lastName is working since $startYear"
    }
}