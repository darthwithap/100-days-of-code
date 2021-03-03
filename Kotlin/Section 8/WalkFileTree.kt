package com.darthwithap.kotlin.section8

import java.io.File

fun main(args: Array<String>) {
    File(".").walkTopDown()
        .filter { it.name.endsWith(".kt") }
        .forEach { println(it) }

}