package twoThree

import java.io.File

object Fourteen {

    private val lines = File("23/input_fourteen.txt").readLines()

//    private val rocks: List<List<Rock>> = lines.mapIndexed { y, it -> it.mapIndexed { x, c -> c.toRock(x, y) } }
    private val rocks: List<String> = lines

    private var rocksCols = listOf<String>()

    init {
        rocksCols = rowsToCols(rocks)
    }

    private fun rowsToCols(rocks: List<String>): MutableList<String> {
        val cols = mutableListOf<String>()
        for (x in rocks.first().indices) {
            val col = StringBuilder(rocks.size).apply {
                for (y in rocks.indices) {
                    append(rocks[y][x])
                }
            }.toString()
            cols.add(col)
        }
        return cols
    }

//    private fun colsToRows(cols: List<List<Rock>>): MutableList<String> {
//        val rows = mutableListOf<String>()
//        for (x in cols.first().indices) {
//            val col = StringBuilder(cols.size).apply {
//                for (y in cols.indices) {
//                    append(cols[y][x].c)
//                }
//            }.toString()
//            rows.add(col)
//        }
//        return rows
//    }

    fun resolve() {
        resolve1()
        resolve2()
    }

    private fun resolve1() {
        val weights: List<Int> = rocksCols.map { col ->
            val stables = col.split('#')
            val stablesSorted = stables.map { it.toCharArray().sortedDescending().joinToString("") }
            val stableSorted: String = stablesSorted.joinToString("#")
            println()
            val length = stableSorted.length
            var weight = 0
            stableSorted.forEachIndexed { index, c -> weight += if(c == 'O') length-index else 0 }
            weight
        }


        println("Sum of weights: ${weights.sum()}")
    }

    private fun resolve2() {
        val history = mutableListOf<Int>()
        val sumHistory = mutableListOf<Int>()
        var shifted: List<String> = rocks
        repeat(137) {
            if(it % 1000000 == 0) println(it/1000000)
            var cols = rowsToCols(shifted)
            shifted = shift(cols)
//            rowsToCols(shifted).forEach { s ->
//                println(s)
//            }
//            println()
            var rows = rowsToCols(shifted)
            shifted = shift(rows)
//            shifted.forEach { s ->
//                println(s)
//            }
//            println()
            cols = rowsToCols(shifted)
            shifted = shift(cols, false)
//            rowsToCols(shifted).forEach { s ->
//                println(s)
//            }
//            println()
            rows = rowsToCols(shifted)
            shifted = shift(rows, false)
//            shifted.forEach { s ->
//                println(s)
//            }
//            println()

            val length = shifted.first().length
            var weight = 0
            shifted.forEach { col ->
                col.forEachIndexed { index, c -> weight += if(c == 'O') length-index else 0 }
            }

            println("Sum of weights2: $weight $it")
            history.add(weight)
            if (sumHistory.contains(weight)) {
                println("have it")
            } else {
                sumHistory += weight
            }
        }
        val resultPos = (6 + (1000000000 - 6) % 7) - 1
        println("result is ${history[resultPos]}")
//        val length = shifted.first().length
//        var weight = 0
//        shifted.forEach { col ->
//            col.forEachIndexed { index, c -> weight += if(c == 'O') length-index else 0 }
//        }
//
//        println("Sum of weights2: $weight")
    }

    private fun shift(rocksCols: List<String>, up: Boolean = true): List<String> = rocksCols.map { col ->
        val stables = col.split('#')
        val stablesSorted = if (up) {
            stables.map { it.toCharArray().sortedDescending().joinToString("") }
        } else {
            stables.map { it.toCharArray().sorted().joinToString("") }
        }
        stablesSorted.joinToString("#")
    }

    fun Char.toRock(x: Int, y: Int) = when (this) {
        'O' -> Rock.Rounded(x, y)
        '#' -> Rock.Cube(x, y)
        '.' -> Rock.Empty(x, y)
        else -> throw IllegalArgumentException("Unknown rock")
    }
    sealed class Rock {

        abstract val x: Int
        abstract val y: Int
        abstract val c: Char

        data class Rounded(override val x: Int, override val y: Int) : Rock() {
            override val c = 'O'
        }

        data class Cube(override val x: Int, override val y: Int) : Rock() {
            override val c = '#'
        }
        data class Empty(override val x: Int, override val y: Int) : Rock() {
            override val c = '.'
        }

    }
}


