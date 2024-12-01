package twoThree

import java.io.File

object Two {

    fun resolve() {
        resolve1()
        resolve2()
    }

    private fun resolve1() {
        val rules = mapOf(
                "red" to 12,
                "green" to 13,
                "blue" to 14
        )

        var sum = 0

        File("23/input_two.txt").forEachLine { line ->
            val split1: List<String> = line.split(":")
            val game: Int = (split1[0].split(" "))[1].toInt()
            var gameOk = true
            val picks: String = split1[1]
            val picksSplit: List<String> = picks.split(";")
            for (pick in picksSplit) {
                val cubes: List<String> = pick.split(",").map { it.substring(1) }
                for (cube in cubes) {
                    val cubeSplit = cube.split(" ")
                    val amount = cubeSplit[0].toInt()
                    val color = cubeSplit[1]
                    //this is actual logic
                    if (amount > (rules[color] ?: 0)) {
                        gameOk = false
                    }
                }
            }
            if (gameOk) {
                sum += game
            }
        }
        println("Ids sum: $sum")
    }

    private fun resolve2() {
        val minimums = mutableMapOf(
                "red" to 0,
                "green" to 0,
                "blue" to 0
        )
        var sum = 0

        File("23/input_two.txt").forEachLine { line ->
            val split1: List<String> = line.split(":")
            val game: Int = (split1[0].split(" "))[1].toInt()

            val picks: String = split1[1]
            val picksSplit: List<String> = picks.split(";")
            for (pick in picksSplit) {
                val cubes: List<String> = pick.split(",").map { it.substring(1) }
                for (cube in cubes) {
                    val cubeSplit = cube.split(" ")
                    val amount = cubeSplit[0].toInt()
                    val color = cubeSplit[1]
                    //this is actual logic
                    if (amount > (minimums[color] ?: 0)){
                        minimums[color] = amount
                    }
                }
            }
            sum += (minimums["red"]?: 0) * (minimums["green"] ?: 0) * (minimums["blue"] ?: 0)
            minimums.clear()
        }
        println("Ids power sum: $sum")
        println("")
    }

}