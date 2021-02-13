package com.darthwithap.kotlin.sec4

fun main(args: Array<String>) {
	val arr1 = arrayOf("John", "Judy", "Jason")
	val arr2 = arrayOf<Long>(1, 2, 3, 4)
	val arr3 = arrayOf(1L, 2L, 3L)
	 val evens = Array(10){i -> (i+1)*2} //Array constructor using lambda expression
	for (num in evens) println(num)

	val nums = Array(100) {i -> i+1}
	val allZeroes = Array(100) {0}
	var anArray: Array<Int>
	anArray = arrayOf(1, 2, 3, 4, 5, 6)
	anArray = Array(10) {i -> (i+1)*5}
	for (i in anArray) {
		print("$i ")
	}

	val mixedArray = arrayOf("Hey", 0, 10, 'b')
	println(mixedArray is Array<*>)

	val array1 = intArrayOf(1, 2, 3, 4, 5) //equivalent to int[] primitive int array in java

	val arr23 = IntArray(5)

}
