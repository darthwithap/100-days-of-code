package com.darthwithap.kotlin.sec4

fun main(args: Array<String>) {
	val int = 10
	var long = 10L

	//no automatic widening from Int to Long in Kotlin
	long = int.toLong() //works

	val byte: Byte = 101
	var short: Short

	short = byte.toShort() //works

	val double = 43.244
	var float = 34.29F
	float = double.toFloat()

	val char = 'w'
	val charInt: Char = 65.toChar() //no automatic conversion, have to explicitly convert to other data type
	println(charInt)


}