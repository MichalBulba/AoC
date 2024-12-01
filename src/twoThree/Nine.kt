package twoThree

import java.io.File

object Nine {

    private val lines = File("23/input_nine.txt").readLines()
            .map { it.split(" ").map(String::toInt) }

    fun resolve() {
        resolve1()
        resolve2()
    }

    private fun resolve1() {
        val rows = lines.map { line: List<Int> ->
            var numbers = line.toMutableList()
            val factors = mutableListOf<Int>()
            do {
                factors += numbers.last()
                numbers = numbers.subtractFromNext().toMutableList()
                println(numbers)
            } while (numbers.any { it != 0})
            factors.sum()
        }
        println("Sum is: ${rows.sum()}")
    }

    private fun resolve2() {
        val rows = lines.map { line: List<Int> ->
            var numbers = line.toMutableList()
            val factors = mutableListOf<Int>()
            do {
                factors += numbers.first()
                numbers = numbers.subtractFromNext().toMutableList()
                println(numbers)
            } while (numbers.any { it != 0})
            println("factors: $factors")
            val reversed = factors.plus(0).reversed()
            println("reversed: $reversed")
            val l = reversed.reduce { acc, i -> i - acc }
            println("L is: $l")
            l
        }
        println("SumFront is: ${rows.sum()}")
    }

    private fun List<Int>.subtractFromNext() = mapIndexedNotNull { i, it ->
        if (i < size - 1) {
            this[i + 1] - it
        } else null
    }
}