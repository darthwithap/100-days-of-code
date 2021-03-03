package com.darthwithap.kotlin.section8

import java.io.File

fun main(args: Array<String>) {
    val fileReader = File("main.txt").reader()
    val lines = fileReader.readLines()
    println(lines)
    // JAVA - new InputStreamReader(new FileInputStreamReader(new File("main.txt")), "UTF-8")
    lines.forEach {
        println(it)
    }
    val reader = fileReader.readText()

    println(fileReader.use { it.readText() }) //closable
    println(reader)
    fileReader.close()
    //can also do buffered reader
    //Direct reader of the file
    val linez = File("main.txt").readText()
    File("main.txt").reader().forEachLine { println(it) }
    File("main.txt").reader().useLines { it.forEach { println(it) } }// get a sequence so need a terminal operation, here outer it is a sequence
}