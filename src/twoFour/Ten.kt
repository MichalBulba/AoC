package twoFour

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.io.File
import java.time.Instant
import java.util.concurrent.atomic.AtomicInteger

object Ten {

    private val map = mutableListOf<List<Int>>()
    private val starts = mutableListOf<Point>()
    private var width = 0
    private var height = 0

    /**
     * Single thread
     * Run for: 15ms
     * Score: 719
     * Part2
     * Run for: 7ms
     * Score: 1530
     *
     * Dispatched
     * Run for: 66ms
     * Score: 719
     * Part2
     * Run for: 17ms
     * Score: 1530
     *
     * Lol, the dispatched version is slower than the single thread version
     */

    fun resolve() {
        println("Parallel")
        resolve1()
        resolve2()
        println("Dispatched")
        resolve1bNoInput()
        resolve2b()
    }

    private fun resolve1() {
//        val startT = Instant.now().toEpochMilli()
        var lineN = 0
        File("24/input_ten.txt").forEachLine { line ->
            line.toCharArray().toList().mapIndexed { index, it ->
                val h = it.toString().toInt()
                if (h == 0) {
                    starts.add(Point(index, lineN))
                }
                h
            }.let {
                map.add(it)
            }
            lineN++
        }
        width = map[0].size
        height = map.size

        val startT = Instant.now().toEpochMilli()
        var score = 0
        starts.forEach { start ->
            var options = mutableSetOf(start)
            do {
                options = options.flatMap {
                    it.neighborsOf(1) - it
                }.toMutableSet()
            } while (options.any { map[it.y][it.x] < 9 })
            score += options.size
        }

        val endT = Instant.now().toEpochMilli()
        println("Run for: ${endT - startT}ms")
        println("Score: $score")
    }

    private fun resolve1bNoInput() {
//        var lineN = 0
//        File("24/input_ten.txt").forEachLine { line ->
//            line.toCharArray().toList().mapIndexed { index, it ->
//                val h = it.toString().toInt()
//                if (h == 0) {
//                    starts.add(Point(index, lineN))
//                }
//                h
//            }.let {
//                map.add(it)
//            }
//            lineN++
//        }
//        width = map[0].size
//        height = map.size

        val startT = Instant.now().toEpochMilli()
        val score = AtomicInteger(0)
        starts.forEach { start ->
            runBlocking(Dispatchers.Default) {
                var options = mutableSetOf(start)
                do {
                    options = options.flatMap {
                        it.neighborsOf(1) - it
                    }.toMutableSet()
                } while (options.any { map[it.y][it.x] < 9 })
                score.getAndAdd(options.size)
            }
        }

        val endT = Instant.now().toEpochMilli()
        println("Run for: ${endT - startT}ms")
        println("Score: ${score.get()}")
    }

    private fun resolve2() {
        println("Part2")
        val startT = Instant.now().toEpochMilli()

        var score = 0
        starts.forEach { start ->
            var options = mutableListOf(start)
            do {
                options = options.flatMap {
                    it.neighborsOf(1) - it
                }.toMutableList()
            } while (options.any { map[it.y][it.x] < 9 })
            score += options.size
        }

        val endT = Instant.now().toEpochMilli()
        println("Run for: ${endT - startT}ms")
        println("Score: $score")
    }

    private fun resolve2b() {
        println("Part2")
        val startT = Instant.now().toEpochMilli()

        val score = AtomicInteger(0)
        starts.forEach { start ->
            runBlocking(Dispatchers.Default) {
                var options = mutableListOf(start)
                do {
                    options = options.flatMap {
                        it.neighborsOf(1) - it
                    }.toMutableList()
                } while (options.any { map[it.y][it.x] < 9 })
                score.getAndAdd(options.size)
            }
        }

        val endT = Instant.now().toEpochMilli()
        println("Run for: ${endT - startT}ms")
        println("Score: ${score.get()}")
    }

    private fun Point.neighborsOf(diff: Int) = listOf(
        this.move(Vector.UP),
        this.move(Vector.DOWN),
        this.move(Vector.LEFT),
        this.move(Vector.RIGHT)
    ).filter { it.checkInArrayRange(width, height) && checkDiff(it) == diff }

    private fun Point.checkDiff(point: Point) = map[point.y][point.x] - map[this.y][this.x]

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
