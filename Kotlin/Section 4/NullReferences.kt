package com.darthwithap.kotlin.sec4

import javax.lang.model.element.NestingKind

fun main(args: Array<String>) {
	var str: String? = "Not Null "
	str?.toUpperCase() // short hand for null check
	if (str is String) println(str?.toUpperCase())
	str = null
	println(str)
	val str2 = str ?: "This is default value"
	println(str2)

	//val countryCode: String? = bankBranch?.address?.country?.countryCode - for nested not null checks
	val arr: Any = arrayOf(1, 2, 3, "Element")
	var str3 = arr as? String
	println(str3)
	print(str3?.toUpperCase())

	//to assert expression is now null
	val str4: String? = "This isn't null"
	val str5 = str4!!.toUpperCase() //To throw null point exception and not 'null', very certain that str4 is not null
	print(str5)
	val str6: String? = "Not null."
	printSomething(str6!!)
	// can assert str6 not null by str6!!
	// or 
	// Use 'let' which is short hand for if (str6 != null) printSomething(str6)
	 str6?.let { printSomething(it) } // if str6 not null let function call in {..} go ahead

	//Null Reference in Arrays
	val nullInts = arrayOfNulls<Int?>(5)
	for (i in nullInts) println(i)
	nullInts[2].toString()
}

fun printSomething(text: String) {
	println(text)
}
