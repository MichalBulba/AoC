package twoTwo

import java.io.File

object Two {
    fun resolve() {
        resolve1()
        resolve2()
    }

    private fun resolve1() {
        var score = 0
        File("22/input_two.txt").forEachLine {
            score += score(it)
        }
        println("My score is $score")
    }

    private fun score(shapes: String) = when(shapes) {
        "A X" -> 1 + 3
        "B Y" -> 2 + 3
        "C Z" -> 3 + 3
        "B X" -> 1 + 0
        "C Y" -> 2 + 0
        "A Z" -> 3 + 0
        "C X" -> 1 + 6
        "A Y" -> 2 + 6
        "B Z" -> 3 + 6
        else -> 0
    }

    private fun resolve2() {
        var score = 0
        File("22/input_two.txt").forEachLine {
            score += score2(it)
        }
        println("My score adjusted is $score")
    }

    private fun score2(shapes: String) = when(shapes) {
        "A X" -> 3 + 0
        "B X" -> 1 + 0
        "C X" -> 2 + 0
        "A Y" -> 1 + 3
        "B Y" -> 2 + 3
        "C Y" -> 3 + 3
        "A Z" -> 2 + 6
        "B Z" -> 3 + 6
        "C Z" -> 1 + 6
        else -> 0
    }
}