package twoFour

import java.io.File
import java.time.Instant
import kotlin.math.absoluteValue

object Twelve {

    private val map = mutableListOf<List<Char>>()
    private val beenThere = mutableSetOf<Point>()

    private val gardens = hashMapOf<Char, MutableList<Garden>>()
    private val gardensCombined = hashMapOf<Char, MutableList<Garden>>()

    private var width = 0
    private var height = 0

    private data class Garden(val perimeter: Int, val plots: Set<Point>) {
        val fencePrice = perimeter * plots.size
    }

    fun resolve() {
        resolve1()
        resolve2()
    }

    private fun resolve1() {
        val startT = Instant.now().toEpochMilli()
        File("24/input_twelve.txt").forEachLine { line ->
            map.add(line.toCharArray().toList())
        }
        width = map[0].size
        height = map.size

        //gathers but some gardens adjacent are in chunks
        for (i in map.indices) {
            for (j in map[i].indices) {
                val c = map[i][j]
                val p = Point(j, i)
                val n = p.neighborsOf(c)
                val nn = n.size
                val perimeter = nn.toPerimeter()

                gardens.getOrPut(c) { mutableListOf() }.let { it: MutableList<Garden> ->
                    it.findGarden(p)?.let { garden ->
                        val copy = garden.copy(perimeter = garden.perimeter + perimeter, plots = garden.plots + p)
                        it.remove(garden)
                        it.add(copy)
                    } ?: it.add(Garden(perimeter, mutableSetOf(p)))
                }
            }
        }

        //combine gardens with neighbours
        gardens.forEach {
            val gardenss: MutableList<Garden> = it.value
            val combined = combine(gardenss)
            gardensCombined[it.key] = combined.toMutableList()
        }

        gardens.forEach { (c, garden) ->
            println("Garden $c has ${garden.size} plots with perimeter ${garden.sumOf { it.perimeter }} and price ${garden.sumOf { it.fencePrice }} ")
        }

        val sum = gardensCombined.values.flatten().sumOf { it.fencePrice }
        val endT = Instant.now().toEpochMilli()
        println("Run for: ${endT - startT}ms")
        println("Price: $sum")
    }

    private fun resolve2() {
        println("Part2")
        val startT = Instant.now().toEpochMilli()

        val endT = Instant.now().toEpochMilli()
        println("Run for: ${endT - startT}ms")
    }

    private fun combine(gardens: List<Garden>) : List<Garden> {
        return if (gardens.size >  1) {
            val first = gardens[0]
            val neighbours = gardens.filter { it.isNeighbor(first) } - first
            if(neighbours.isNotEmpty()) {
                val combined = neighbours.fold(first) { acc, garden -> acc.combine(garden) }
                val rest = gardens - neighbours.toSet() - first
                combine(listOf(combined) + rest)
            } else {
                gardens.take(1) + combine(gardens.drop(1))
            }
        } else {
            gardens
        }
    }

    private fun Garden.isNeighbor(garden: Garden) =
        this.plots.any { garden.plots.any { point -> it.neighborsOf(point).isNotEmpty() } }

    private fun Garden.combine(garden: Garden) = Garden(this.perimeter + garden.perimeter, this.plots + garden.plots)

    private fun List<Garden>.findGarden(point: Point) =
        this.find { it.plots.any { point1 -> point1.neighborsOf(point).isNotEmpty() } }

    private fun Point.neighborsOf(diff: Int) = listOf(
        this.move(Vector.UP),
        this.move(Vector.DOWN),
        this.move(Vector.LEFT),
        this.move(Vector.RIGHT)
    ).filter { it.checkInArrayRange(width, height) && it.checkDiff(this) == diff }

    private fun Point.neighborsOf(p: Point) = listOf(
        this.move(Vector.UP),
        this.move(Vector.DOWN),
        this.move(Vector.LEFT),
        this.move(Vector.RIGHT)
    ).filter {
        it.checkInArrayRange(width, height) &&
                ((p.checkLenX(this).absoluteValue == 1 && p.checkLenY(this) == 0)
                        ||
                        (p.checkLenY(this).absoluteValue == 1 && p.checkLenX(this) == 0))
    }

    private fun Point.neighborsOfCached(c: Char): List<Point> = gardens[c]!!.flatMap { it.plots }.filter { it: Point ->
        it.checkInArrayRange(width, height) &&
                ((it.checkLenX(this).absoluteValue == 1 && it.checkLenY(this) == 0)
                        ||
                        (it.checkLenY(this).absoluteValue == 1 && it.checkLenX(this) == 0))
    }

    private fun Point.neighborsOf(c: Char) = listOf(
        this.move(Vector.UP),
        this.move(Vector.DOWN),
        this.move(Vector.LEFT),
        this.move(Vector.RIGHT)
    ).filter { it.checkInArrayRange(width, height) && it.checkSame(c) }

    private fun Int.toPerimeter() = 4 - this

    private fun Point.checkDiff(point: Point) = map[point.y][point.x] - map[this.y][this.x]

    private fun Point.checkLenX(point: Point) = point.x - this.x
    private fun Point.checkLenY(point: Point) = point.y - this.y

    private fun Point.checkSame(c: Char) = map[this.y][this.x] == c

    private data class Point(val x: Int, val y: Int) {

        fun move(vector: Vector) = Point(this.x + vector.x, this.y + vector.y)
        fun moveBack(vector: Vector) = Point(this.x - vector.x, this.y - vector.y)
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
