package twoTwo

import java.io.File

object One {
    fun resolve() {
        resolve1()
        resolve2()
    }

    private fun resolve1() {
        var winner = 0
        var highest = 0

        var currentP = 0
        var currentC = 0
        File("22/input_one.txt").forEachLine {
            if (it != "") {
                currentC += it.toInt()
            } else {
                currentP += 1
                if (currentC > highest) {
                    highest = currentC
                    winner = currentP
                }
                currentC = 0
            }
        }
        println("The most calories of $highest carries elf nr $winner")
    }

    private fun resolve2() {
        var currentP = 0
        val elves = mutableListOf<Int>()
        elves.add(0)
        File("22/input_one.txt").forEachLine {
            if (it != "") {
                elves[currentP] += it.toInt()
            } else {
                currentP += 1
                elves.add(0)
            }
        }
        println("Top 3 elves carry ${elves.sortedDescending().take(3).sum()}")
    }
}