package twoThree

import java.io.File
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

object Eleven {

    private val OLD = 1
//    private val OLD = 10000
    private val OlD_CLEVER = 1000000L
    private val lines = File("23/input_eleven.txt").readLines()

    private val emptyRows = mutableListOf<Int>()
    private val expandedRows: List<String> = lines.flatMapIndexed { i, line: String ->
        if (line.all { c -> c == '.' }) {
            emptyRows.add(i)
            List(OLD) { line }
        } else listOf(line)
    }

    private val emptyCols = mutableListOf<Int>()
//    private val emptyCols2 = mutableListOf<Int>()
    private var expandedCols = listOf<String>()

    init {
        for (i in expandedRows[0].indices) {
            val col: List<Char> = expandedRows.map { it[i] }
            if(col.all { c -> c == '.' }) {
                emptyCols.add(i)
            }
        }

        expandedCols = expandedRows.map {row ->
            val sb: StringBuilder = StringBuilder(row.length + emptyCols.size)
            row.forEachIndexed { i, c ->
                sb.append(if(emptyCols.contains(i)) ".".repeat(OLD) else c)
            }

            sb.toString()
        }

//        for (i in expandedCols[0].indices) {
//            val col: List<Char> = expandedCols.flatMap { listOf(it[i]) }
//            if(col.all { c -> c == '.' }) {
//                emptyCols2.add(i)
//            }
//        }
    }


    fun resolve() {
        resolve1()
//        resolve2()
    }

    private fun resolve1() {
        println("Resolve 1")
        val galaxies = expandedCols.flatMapIndexed { y, row: String ->
            row.mapIndexedNotNull { x, c -> if (c == '#') Galaxy(x, y) else null }
        }

        val pairs = galaxies.asSequence()
                .flatMap { first ->
                    galaxies.asSequence()
                            .filter { second -> first != second }
                            .map { second -> Pair(first, second) }
                }
                .toList()

        val pairsFiltered = mutableListOf<Pair<Galaxy, Galaxy>>()
        pairs.forEach {
            if(!pairsFiltered.contains(it) && !pairsFiltered.contains(it.second to it.first)) {
                pairsFiltered.add(it)
            }
        }

        var lengths = 0L
        pairsFiltered.forEach {
            val d = it.first.distance(it.second)
            lengths += d
            println("Distance ${it.first} ${it.second} $d")
        }

        println("Distance: $lengths")

        var lengthsRelative = 0L
        pairsFiltered.forEach {
            val d = it.first.distanceRelative(it.second)
            lengthsRelative += d
            println("Distance ${it.first} ${it.second} $d")
        }

        println("Distance relative: $lengthsRelative")
    }

    private fun resolve2() {

        println("")
    }

    data class Galaxy(val x: Int, val y: Int) {

        fun distance(other: Galaxy): Int {
            val xx = abs(x - other.x)
            val yy = abs(y - other.y)
//            return sqrt((xx*xx + yy*yy).toDouble()).toInt() -1
            return xx + yy
        }

        fun distanceRelative(other: Galaxy): Long {
            var distance: Long = this.distance(other).toLong()
            val rows = emptyRows.count { it > min(y, other.y) && it < max(y, other.y) }.toLong() * (OlD_CLEVER - 1)
            val cols =  emptyCols.count { it > min(x, other.x) && it < max(x, other.x) }.toLong() * (OlD_CLEVER - 1)
            println("rows $rows, cols $cols")
            return distance + rows + cols
        }
    }
}


