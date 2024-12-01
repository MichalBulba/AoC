package twoThree

import java.io.File
import kotlin.math.pow

object Four {

    private val lines = File("23/input_four.txt").readLines()
    private val lineLength = lines[0].length
    fun resolve() {
//        resolve1()
        resolve2()
    }

    private fun resolve1() {
        var sum = 0.0
        lines.forEachIndexed { y, line ->
            val (card, numbersSS) = line.split(":")
            val (d, cardId) = card.split(" ")
            val (yoursS, winningS) = numbersSS.split("|")
            val yours: List<String> = yoursS.trim().split(" ").filter { it.isNotEmpty() }
            val winning: List<String> = winningS.trim().split(" ").filter { it.isNotEmpty() }

            val numbers = yours.size
            val trueWinsNegative = yours.toMutableList()
            trueWinsNegative.removeAll(winning)
            val wins = numbers-trueWinsNegative.size
            if (wins > 0) {
                val pow = (2.0).pow(wins-1)
                sum += pow
                println(sum)
            }
        }

        println("Winnig sum: $sum")
    }

    private fun resolve2() {
        var cards = 0
        val factors = MutableList(lineLength) { 0 }
        lines.forEachIndexed { y, line ->
            val (card, numbersSS) = line.split(":")
            val (d, cardId) = card.split(" ")
            val (yoursS, winningS) = numbersSS.split("|")
            val yours: List<String> = yoursS.trim().split(" ").filter { it.isNotEmpty() }
            val winning: List<String> = winningS.trim().split(" ").filter { it.isNotEmpty() }

            val numbers = yours.size
            val trueWinsNegative = yours.toMutableList()
            trueWinsNegative.removeAll(winning)
            val wins = numbers-trueWinsNegative.size
            val factor = (factors.removeFirstOrNull() ?: 0) + 1
            cards += factor
            for (i in (0 until wins)) {
                if (i < factors.size) factors[i] += factor
                else factors.add(factor)
            }
            println("cards $cards, wins $wins, $factors")
        }

        println("Winnig sum: $cards")
    }
}