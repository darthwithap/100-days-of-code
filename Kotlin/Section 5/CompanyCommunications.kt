package com.darthwithap.kotlin.challenge1

import java.net.InterfaceAddress
import java.time.Year

fun main(args: Array<String>) {
    println(CompanyCommunications.getTagLine())
    println(CompanyCommunications.getCopyrightLine())
    println(SomeClass.accesPrivVar()) //just like a static method in java w/o instance of class
    println(SomeClass.justAssign("Hey there, JUst Assign!").str)
    println(SomeClass.caseString("Hey there, case AsIgN!", false).str)

    wantsSomeInterface(object: SomeInterface { // not a singleton, always a new instance created
        override fun mustImplementFunction(num: Int): String = num.toString()
    }) //anonymous instance w 'object' keyword

    println(Department.HR.getDeptInfo())
    println(Department.ACCOUNTING.getDeptInfo())
}
enum class Department(val fullName: String, val numOfEmployees: Int) {
    HR("Human Resources", 10), IT("Information Technology", 5), ACCOUNTING("Accounting", 4), SALES("Sales", 10), SECURITY("Security", 5);

    fun getDeptInfo() = "The $fullName has $numOfEmployees employees"
}

// singleton in kotlin
object CompanyCommunications {
    val currYear = Year.now().value
    fun getTagLine() ="Our company tagline!"
    fun getCopyrightLine() = "Copyright \u00A9 $currYear Our company. All rights reserved"
}

// so that constructor isnt accessible outside, only via the functions created
class SomeClass private constructor(val str: String) {

    companion object {
        private var privVar = 6

        fun accesPrivVar() = "Accessign privVar without an instance of SomeClass $privVar"

        fun justAssign (str: String): SomeClass {   //FACTORY METHOD
            return SomeClass(str)
        }
        fun caseString(str: String, case: Boolean): SomeClass { //FACTORY METHOD
            if (case) return SomeClass(str.toLowerCase())
            else return SomeClass(str.toUpperCase())
        }
    }
}

interface SomeInterface {
    fun mustImplementFunction(num: Int): String
}

fun wantsSomeInterface(si: SomeInterface) {
    println("Printing from wantsImplement: ${si.mustImplementFunction(30)}")
}

//internal - visibile withing the module but not another module
// helps when a module is a dependancy for another module but want to hide one function.

