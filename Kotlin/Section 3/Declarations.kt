package com.darthwithap.kotlin.helloworld
typealias EmployeeSet = Set<Employee>

fun main(args: Array<String>) {
    var num = 20
    num = 25

    val emp1 = Employee("Parth Takkar", 1)
    emp1.name = "Parth Takkar 2"
    var emp2 = Employee("Parth", 3023)
    val num2 = 100
    println(emp2)

    if (num<num2) {
        emp2 = Employee("John Smith", 2)
    } else emp2 = Employee("John Smith", 3)

    println(emp2)

    println("""Hello my name is parth and this is file path: C:\User\Parth\Users\Code.""")

    println("""Hey
        |How are
        |you!
    """.trimMargin())

    print("""Hey
        `How are
        `you!
    """.trimMargin("`"))
}

class Employee (var name: String, val id: Int) {
    override fun toString(): String {
        return "Employee (name=$name, id=$id)"
    }
}