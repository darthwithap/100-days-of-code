package com.darthwithap.kotlin.challenge1

// no instance of an interface
interface MyInterface {
    fun MyFun(str: String): String
    val num: Int
    val num2: Int
}

interface SubInterface: MyInterface {
    fun MySubFun(str: String): String
}

class Something(): SubInterface {
    override val num = 20
    override val num2: Int
        get() = 10 * num
    override fun MySubFun(str: String): String {
        return str
    }

    override fun MyFun(str: String): String {
        return str
    }

}