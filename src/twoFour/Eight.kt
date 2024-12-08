package twoFour

import java.io.File
import java.time.Instant
import kotlin.math.sqrt

object Eight {

    private val map = mutableListOf<String>()
    private val antennas = mutableSetOf<Point>()
    private val antennasM = hashMapOf<Char, MutableList<Point>>()
    private var width = 0
    private var height = 0

    fun resolve() {
        resolve1()
        resolve2()
    }

    private fun resolve1() {
        val startT = Instant.now().toEpochMilli()
        File("24/input_eight.txt").forEachLine { line ->
            map.add(line)
        }

        map.forEachIndexed { y, line ->
            line.forEachIndexed { x, c ->
                if (c != '.' && c != '#') {
                    antennas += Point(x, y)
                    antennasM[c]?.add(Point(x, y)) ?: antennasM.put(c, mutableListOf(Point(x, y)))
                }
            }
        }
        width = map[0].length
        height = map.size

        val antinodes = mutableSetOf<Point>()
        antennasM.forEach { (c, points) ->
            antinodes += points.findNodes()
        }
        val endT = Instant.now().toEpochMilli()

        println("Found ${antinodes.size} antinodes")
        println("Run for: ${endT - startT}ms")
    }

    private fun List<Point>.findNodes(): List<Point> {
        val nodes = mutableListOf<Point>()
        //takes every pair of points and calculates the diff between them
        for (i in indices) {
            for (j in i + 1 until this.size) {
                val diff = this[j].diff(this[i])
                val a = this[i].moveBack(diff)
                val b = this[j].move(diff)
                if(a.checkInArrayRange(width, height)){
                    nodes += a
                }
                if(b.checkInArrayRange(width, height)){
                    nodes += b
                }
            }
        }

        return nodes
    }

    private fun List<Point>.findAllNodes(): List<Point> {
        val nodes = mutableListOf<Point>()
        //takes every pair of points and calculates the diff between them
        for (i in indices) {
            for (j in i + 1 until this.size) {
                val diff = this[j].diff(this[i])
                var a = this[i].moveBack(diff)
                while(a.checkInArrayRange(width, height)){
                    nodes += a
                    a = a.moveBack(diff)
                }
                var b = this[j].move(diff)
                while(b.checkInArrayRange(width, height)){
                    nodes += b
                    b = b.move(diff)
                }
            }
        }

        return nodes
    }

    private fun resolve2() {
        println("Part2")
        val startT = Instant.now().toEpochMilli()

        val antinodes = mutableSetOf<Point>()
        antennasM.forEach { (c, points) ->
            antinodes += points.findAllNodes()
        }

        val antinodesAntennas = antennasM.values.filter { it.size > 1 }.flatten()
        val all: Set<Point> = antinodesAntennas.toSet() + antinodes

        val endT = Instant.now().toEpochMilli()

        println("Found ${all.size} all")
        println("Run for: ${endT - startT}ms")
    }

    private data class Point(val x: Int, val y: Int) {

        fun move(vector: Vector) = Point(this.x + vector.x, this.y + vector.y)
        fun moveBack(vector: Vector) = Point(this.x - vector.x, this.y - vector.y)

        fun move(point: Point) = Point(this.x + point.x, this.y + point.y)
        fun moveBack(point: Point) = Point(this.x - point.x, this.y - point.y)

        fun diff(point: Point) = Point(this.x - point.x, this.y - point.y)
        fun diffAbs(point: Point) = Point(Math.abs(this.x - point.x), Math.abs(this.y - point.y))

        fun asVectorLength() = sqrt((this.x * this.x + this.y * this.y).toDouble())

        fun checkInArrayRange(x: Int, y: Int) = this.x in 0 until x && this.y in 0 until y
    }

    private enum class Vector(val x: Int, val y: Int) {

        UP(0, -1) {
            override fun rotate() = RIGHT

        },
        DOWN(0, 1) {
            override fun rotate() = LEFT

        },
        LEFT(-1, 0) {
            override fun rotate() = UP

        },
        RIGHT(1, 0) {
            override fun rotate() = DOWN

        };

        abstract fun rotate(): Vector

    }
}
