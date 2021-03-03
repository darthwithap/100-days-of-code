package com.darthwithap.kotlin.section6

fun main(args: Array<String>) {
    val setInts = setOf(15, 10, 19, 5, 23, 5, 43)
    println(setInts.filter { it % 2 != 0 })

    val immutableMap = mapOf<Int, Car>(
        1 to Car("green", "Toyota", 2015),
        2 to Car("red", "Honda", 2018),
        3 to Car("Blue", "Suzuki", 2011))

    val mutableMap = mutableMapOf<Int, Car>(
        1 to Car("green", "Toyota", 2015),
        2 to Car("red", "Honda", 2018),
        3 to Car("Blue", "Suzuki", 2011))

    println(immutableMap.filter { it.value.year == 2018 })
    println(mutableMap.filter { it.value.color == "red" })

    val ints = arrayOf(1, 2, 3, 4, 5)
    val add10List = mutableListOf<Int>()
    for (i in ints) {
        add10List.add(i+10)
    }

    println(add10List)

    val add10ListUsingMap = ints.map { it + 10 }
    println(add10ListUsingMap)
    println(add10ListUsingMap.javaClass)
    println(immutableMap.map { it.value.year })
    println(immutableMap.filter { it.value.model == "Toyota"}.map { it.value.color })
    println(immutableMap.all { it.value.year > 2013 })
    println(immutableMap.any { it.value.year > 2013 })
    println(immutableMap.count { it.value.year > 2013 })
    val cars = immutableMap.values //List of all cars
    println(cars.find { it.year > 2013 })
    println(cars.all { it.year > 2013 })
    println(cars.groupBy { it.color}) //Map
    println(cars.groupBy { it.color}.javaClass)
    println(immutableMap.toSortedMap())
    println(cars.sortedBy { it.year })


    listOf("Mary", "Jude", "Peter").asSequence().
            filter{ println("filtering $it"); it[0] == 'J'}
        .map{ println("Mapping $it"); it.toUpperCase()}
}