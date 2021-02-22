package com.darthwithap.kotlin.challenge1

fun main(args: Array<String>) {
    val laserPrinter = LaserPrinter("HP 201")
    laserPrinter.printModel()
}

abstract class Printer(val modelName: String) {
    open fun printModel() = println("Model is: $modelName" +
            "")
    abstract fun bestSellingPrice(): Double
}

open class LaserPrinter(modelName: String): Printer(modelName) {
    final override fun printModel() = println("Laser Printer Model is: $modelName")
    override fun bestSellingPrice(): Double = 4599.00
}

class SpecialLaserPrinter(modelName: String): LaserPrinter(modelName) {
    // override implicityly includes open so cant override as it is declared final above.

}

// cant be open or abstract, they are closed tight- they can extend other classes
data class NewClass (val num: Int)
