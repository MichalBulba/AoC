package twoThree

import java.io.File

object Seven {

    private val lines = File("23/input_seven.txt").readLines().map {line ->
        val (hand, bet) = line.split(" ")
        Player(hand, hand.groupBy { it }, bet.toInt())
    }


    fun resolve() {
//        resolve1()
        resolve2()
    }

    private fun resolve1() {
        var winnings = 0
//        val playersSorted = lines.sortedByDescending { it.handGroups.strength() }
        val playersSorted: List<Player> = lines.sortedWith(
            compareBy<Player> {
                it.handGroups.strength()
            }.thenBy {
                val replaced = it.hand
                        .replace("T", "a")
                        .replace("J", "b")
                        .replace("Q", "c")
                        .replace("K", "d")
                        .replace("A", "e")
                replaced
            }
        )

        playersSorted.forEachIndexed { i, player ->
            winnings += player.bet * (i+1)
        }


        println("Total winnings: $winnings")
    }

    private fun resolve2() {
        var winnings = 0
//        val playersSorted = lines.sortedByDescending { it.handGroups.strength() }
        val playersSorted: List<Player> = lines.sortedWith(
                compareBy<Player> {
                    it.handGroups.strengthWithJ()
                }
                        .thenBy {
                    val replaced = it.hand
                            .replace("T", "a")
                            .replace("J", "1")
                            .replace("Q", "c")
                            .replace("K", "d")
                            .replace("A", "e")
                    replaced
                }
        )

        playersSorted.forEachIndexed { i, player ->
            winnings += player.bet * (i+1)
        }


        println("Total winnings with J: $winnings")
    }

    data class Player(val hand: String, val handGroups: Map<Char, List<Char>>, val bet: Int)

    fun Map<Char, List<Char>>.strength(): Int {
        val amounts = values.map { it.size }
        val longest: Int = amounts.max()
        val hasThree = amounts.contains(3)
        val two = amounts.count { it == 2 }
        return when {
            longest == 5 -> 7
            longest == 4 -> 6
            hasThree && two > 0 -> 5
            hasThree -> 4
            two > 1 -> 3
            two == 1 -> 2
            else -> 1
        }
    }

    fun Map<Char, List<Char>>.strengthWithJ(): Int {
        val amounts = values.map { it.size }
        val longest: Int = amounts.max()
        val hasThree = amounts.contains(3)
        val two = amounts.count { it == 2 }
        val js = this['J']?.size ?: 0
        return when {
            longest == 5 -> 7
            longest == 4 ->
                if (js == 1) 7
                else if (js == 4) 7
                else 6

            hasThree && two > 0 -> {
                if (js == 3) 7
                else if (js == 2) 7
                //if (js == 1)
                else 5
            }
            hasThree -> {
                if (js == 3) 6
                else if (js == 1) 6
                else 4
            }
            two > 1 -> {
                if(js == 2) 6
                else if(js == 1) 5
                else 3
            }

            two == 1 -> {
                if (js == 2) 4
                else if (js == 1) 4
                else 2
            }
            else -> {
                if(js == 1) 2
                else 1
            }
        }
    }
}