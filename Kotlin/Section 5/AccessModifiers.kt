package com.darthwithap.kotlin.challenge1

fun main(args: Array<String>) {
    val emp = Employee("Parth")
    println(emp.firstName)
    val emp2 = Employee("John", false)
    println(emp2.fullTime)

    val empp = EmployeePrimary("Parth")
    val empp2 = EmployeePrimary("Parth", false)
    println("${empp.name} and ${empp2.working}")
    empp.working = true //setter
    empp.working //getter
}

//Getter and setters are generated automatically in Kotlin for public properties
// no point in using properties private as the above calls are under the hood getters and setters

class Employee (val firstName: String) {
    var fullTime: Boolean = true

    // secondary constructor
    constructor(firstName: String, fullTime: Boolean): this(firstName)  {
        this.fullTime = fullTime
    }
}

class EmployeePrimary (val name: String, var working: Boolean = true) // default value in primary constructor and no need of secondary constructor

class CustomEmployee (val name: String, newHire: Boolean) { // for custom getter, setter don't declare inside primary constructor
    var newHire = newHire // custom getters come directly after declaration in class
    get() {
        println("Custom getter for newHire")
        return field //field is an identifier here
    }
    set(value) {
        println("Running custom setter for newHire")
        field = value
    }
}
class Demo {
    val dummy: String

    // secondary constructor without a primary
    constructor(dummy: String) {
        this.dummy = dummy
    }
}