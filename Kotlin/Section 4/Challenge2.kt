package com.darthwithap.kotlin.sec4

fun main(args: Array<String>) {
	//1.
	val float = -3847.84f
	val float2: Float = (-3847.384).toFloat()

	//2.
	val f1: Float? = -3847.384f
	val f2: Float? = (-3847.384).toFloat()

	//3.
	val arr = shortArrayOf(1, 2, 3, 4, 5)
	val arr2: Array<Short> = arrayOf(1, 2, 3, 4, 5)

	//4.
	val arr3: Array<Int?> = Array(40) {i -> (i+1) * 5}
	for (i in arr3) print("$i ")
	println()

	//5.
	val arr4 = charArrayOf('a', 'b', 'c')

	//6.
	val x: String? = "I AM IN UPPERCASE"
	val y = x?.toLowerCase() ?: "I give up"

	//7.
	val z: String? = x
	z?. let { println(it.toLowerCase().replace("am", " am not")) }

	//8.
	val noNullVar: Int? = null
	noNullVar!!.toDouble()

}