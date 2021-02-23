package com.darthwithap.kotlin.section6

fun main(args: Array<String>) {
    for (i in 5..50 step 5) println(i)
    for (i in -50..0) println(i)
    var sum: Int
    var temp = 0
    var curr = 1
    println(temp)
    println(curr)
    for (i in 1..13) {
        sum = curr + temp
        println(sum)
        temp = curr
        curr = sum
    }

    iloop@ for (i in 1..5) {
        println(i)
         if (i==2) break
        for (j in 11..20) {
            println(j)
            for (k in 100 downTo 90) {
                println(k)
                if (k < 99) continue@iloop
            }
        }
    }

    val num = 43
    val dnum: Double = when {
        num > 100 -> -234.567
        num < 100 -> 4444.555
        else -> 0.0
    }
    print(dnum)
}