package com.darthwithap.kotlin.section6

import java.awt.font.NumericShaper
import java.lang.IllegalArgumentException
import javax.print.attribute.IntegerSyntax

fun main(args: Array<String>) {
    println(getNumber("29")?: throw IllegalArgumentException("Number isnt an Int"))
    notImplementedYet("Trial")
}

fun notImplementedYet(str: String): Nothing {
    throw IllegalArgumentException("Implement me!")
}

fun getNumber(str: String): Int? {
    return try { Integer.parseInt(str) }
        catch (e: NumberFormatException) { null }
    finally { println("I am in finally block") } // cant return anything from finally block
}