package com.darthwithap.kotlin.section6

fun main(args: Array<String>) {

    val strings = listOf("spring", "summers", "winter", "autumn")
    println(strings.javaClass) // java.util.Arrays$ArrayList - non mutable only via getters and setters
    val notNUllList = listOfNotNull("hello", null, "goodbye")

    val emptyList = emptyList<String>() //kotlin.collections.EmptyList

    if (!emptyList.isEmpty()) {
        println(emptyList[0])
    }
    println(notNUllList)
    val arrayList = arrayListOf(1, 2, 3, 4)
    val mutableList = mutableListOf<Int>(1, 2, 3, 4)

    val array = arrayOf("this", "that")
    val list = listOf(*array)
    println(list)
    println(array.toList())


    //collection functions
    println(strings.last())
    println(strings.asReversed())
    if (strings.size > 5) println("Strings of size 5")
    println(strings.getOrNull(5)) //check if exists then print else null
    val ints = listOf(1, 2, 3, 4, 5, 3, 4, 5)
    println(ints.maxOrNull())
    println(strings.zip(ints)) //zip creates pairs
    val mergedList = strings + ints
    println(mergedList)
    println(ints.distinct())
    println(ints.union(strings))

    val ints2 = ints.toMutableList()
    ints2.add(10)
    println(ints2)

    //MAPS
    val immutableMap = mapOf<Int, Car>(
        1 to Car("green", "Toyota", 2015),
        2 to Car("red", "Honda", 2018),
        3 to Car("Blue", "Suzuki", 2011))

    println(immutableMap.javaClass)
    println(immutableMap)

    val mutableMap = hashMapOf("My car" to  Car("green", "Toyota", 2015),
    "Your Car" to Car("red", "Honda", 2018))

    mutableMap.put("Their Car", Car("Blue", "Suzuki", 2011))

    println(mutableMap.javaClass)
    println(mutableMap)

    //deconstructing declarations
    val (first, second) = Pair(240, "Parth")
    println("$first and $second")

    for(e in mutableMap){
        println(e.key)
        println(e.value)
    }

    for((a, b) in mutableMap) {
        println(a)
        println(b)
    }

    //deconstructing declarations
    val (col, mod, yr) = Car("colour", "model", 2000)
    println("$col + $mod + $yr")

    val (col2, mod2, yr2) = Car2("colour", "model", 2000)
    println("$col2 + $mod2 + $yr2")

    //SETS
    //LinkedHashSet under the covers
    val setInts = setOf(15, 10, 19, 5, 23, 5, 43)
    println(setInts.plus(72))
    println(setInts.minus(19)) // not really removing as set is immutable and result isnt assigned to anything
    println(setInts.minus(100))
    println(setInts.average())
    println(setInts.drop(2)) // drop first 2 elements

    val mutableIntSet = mutableSetOf(1, 2, 3, 4, 5, 6)
    mutableIntSet.plus(10) // returns a result and dost not change the set
    println(mutableIntSet)
    println(mutableIntSet.add(1))

}

//data classes get deconstructing for free in-built

//MAPS
class Car(val color: String, val model: String, val year: Int) {

    //destructurable class - public properties
    operator fun component1() = color
    operator fun component2() = model
    operator fun component3() = year

}

// no need to explicitly mention destructuring functions get them for free in data class along w toString and equals
data class Car2(val color: String, val model: String, val year: Int)

