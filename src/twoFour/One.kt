package twoFour

import java.io.File
import kotlin.math.absoluteValue

object One {
    private const val SPLITTER = "   "
    fun resolve() {
        resolve1()
        resolve2()
    }

    private fun resolve1() {
        val lefts = mutableListOf<Int>()
        val rights = mutableListOf<Int>()

        File("24/input_one.txt").forEachLine { line ->
            val (left, right) = line.split(SPLITTER)
            lefts.add(left.toInt())
            rights.add(right.toInt())
        }
        lefts.sort()
        rights.sort()

        val distances = lefts.mapIndexed { i, it ->
            (rights[i] - it).absoluteValue
        }

        val distance = distances.sum()

        println("Distance is: $distance")
    }

    private fun resolve2() {
        val lefts = mutableListOf<Int>()
        val rights = mutableListOf<Int>()

        File("24/input_one.txt").forEachLine { line ->
            val (left, right) = line.split(SPLITTER)
            lefts.add(left.toInt())
            rights.add(right.toInt())
        }

        val rightsGrouped: Map<Int, List<Int>> = rights.groupBy { it }

        val similarity = lefts.sumOf {
            it * rightsGrouped.getOrDefault(it, emptyList()).size
        }

        println("similarity score is $similarity")
    }
}