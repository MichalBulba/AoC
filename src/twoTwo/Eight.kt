package twoTwo

import java.io.File

object Eight {

    fun resolve() {
        resolve1()
        resolve2()
    }

    private fun resolve1() {
        val threes = mutableListOf<MutableList<Int>>()
        val visible = mutableSetOf<Pair<Int, Int>>()

        File("22/input_eight.txt").forEachLine { line ->
            threes.add(line.toCharArray().map { it.toString().toInt() }.toMutableList())
        }

        threes.forEachIndexed { y, ints ->
            ints.forEachIndexed { x, three ->
                if (x == 0 || y == 0 || y == threes.size - 1 || x == threes[0].size - 1) {
                    visible.add(y to x)
                } else {
                    if (threes[y].subList(0, x).none { it >= three }) {
                        visible.add(y to x)
                    } else if (threes[y].subList(x + 1, threes[y].size).none { it >= three }) {
                        visible.add(y to x)
                    } else if ((0 until y).map { threes[it][x] }.none { it >= three }) {
                        visible.add(y to x)
                    } else if ((y + 1 until threes.size).map { threes[it][x] }.none { it >= three }) {
                        visible.add(y to x)
                    }

                }
            }
        }

        println("Amount of visible threes is: ${visible.size} ")
//        println(visible)
//        visible.forEach {
//            println(threes[it.first][it.second])
//        }
    }

    private fun resolve2() {
        val threes = mutableListOf<MutableList<Int>>()
//        val visible = mutableSetOf<Pair<Int, Int>>()

        var best = 0
        File("22/input_eight.txt").forEachLine { line ->
            threes.add(line.toCharArray().map { it.toString().toInt() }.toMutableList())
        }

        threes.forEachIndexed { y, ints ->
            ints.forEachIndexed { x, three ->
                if (x == 0 || y == 0 || y == threes.size - 1 || x == threes[0].size - 1) {

                } else {
                    var h = 0
                    var v = 0
                    var t = 0
                    run loop@{
                        threes[y].subList(0, x).reversed().forEach {
                            t++
                            if (it >= three) {
                                return@loop
                            }
                        }
                    }
                    h += t
                    t = 0
                    run loop@{
                        threes[y].subList(x + 1, threes[y].size).forEach {
                            t++
                            if (it >= three) {
                                return@loop
                            }
                        }
                    }
                    h *= t
                    t = 0
                    run loop@{
                        (0 until y).map { threes[it][x] }.reversed().forEach {
                            t++
                            if (it >= three) {
                                return@loop
                            }
                        }
                    }
                    v += t
                    t = 0
                    run loop@{
                        (y + 1 until threes.size).map { threes[it][x] }.forEach {
                            t++
                            if (it >= three) {
                                return@loop
                            }
                        }
                    }
                    v *= t

                    val result = h * v
                    if (result > best) {
                        best = result
                    }
                }
            }
        }



        println("Best scenic score is: $best")
    }

}