fun main(args: Array<String>) {
    val a = "a"
    var b = "b "

    println(a === b) //a and b referential equality test
    println(a == b) //a and b structural equality test

    b = "a"
    println(a === b) //a and b referential equality test
    println(a == b) //a and b structural equality test

    var num = 2988
    var something: Any = "The any type is the root of kotlin the class hierarchy"
    println(something is Unit)
    if (something is String) { // Smart casting to string
        println(something.toUpperCase())
    }

    println("""    1
        |   11
        |  111
        | 1111  
    """.trimMargin())

    print("""   1   1
        1  11
        1 111
        11111
    """.trimMargin("1"))
}