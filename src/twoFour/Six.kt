package twoFour

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import java.lang.Exception
import java.time.Instant
import java.util.concurrent.atomic.AtomicInteger

object Six {

    private val map = mutableListOf<String>()
    private var start = Point(0, 0)
    private var direction = Vector.UP
    private val steps = mutableSetOf<Point>()
    private val steps2 = hashMapOf<Point, MutableSet<Vector>>()

    private fun List<String>.get(x: Int, y: Int) = map[y].elementAt(x)
    private fun List<String>.get(point: Point) = map[point.y].elementAt(point.x)

    fun resolve() {
        resolve1()
        resolve2()
        resolve2b()
    }

    private fun resolve1() {
        val startT = Instant.now().toEpochMilli()
        File("24/input_six.txt").forEachLine { line ->
            map += line
            val x = line.indexOf('^')
            if (x > 0) {
                start = Point(x, map.size)
            }
        }
        var point = start
        try {
            steps += point
            println("Going $direction")
            while (true) {
                point = point.move(direction)
                if (map.get(point) != '#') {
                    println("Step ${steps.size}")
                    steps += point
                } else {
                    point = point.moveBack(direction)
                    direction = direction.rotate()
                    println("Going $direction")
                }
            }
        } catch (t: Throwable) {
            println("done walking")
        }

        println("Walked ${steps.size}")
        val endT = Instant.now().toEpochMilli()

        println("Run for: ${endT - startT}ms")
        println("Expected run for part 2: ${(endT - startT) * 130 * 130 / 1000 / 9 / 9}s")
    }

    private fun resolve2() {
        println("Part2")
        val startT = Instant.now().toEpochMilli()
        var cycles = 0

        for (x in map[0].indices) {
            for (y in map.indices) {
                val poi = Point(x, y)
                direction = Vector.UP
                var point = start

                try {
                    while (true) {
                        point = point.move(direction)
                        if (point == poi) println("on obstacle")
                        if (map.get(point) != '#' && point != poi) {
                            if (steps2.contains(point)) {
                                val had = steps2[point]!!.add(direction)
                                if (!had) {
                                    cycles++
                                    throw Exception("Found cycle")
                                }
                            } else {
                                steps2[point] = mutableSetOf(direction)
                            }
                        } else {
                            point = point.moveBack(direction)
                            direction = direction.rotate()
//                            println("Going $direction")
                        }
                    }
                } catch (t: Throwable) {
                    println("done walking $poi")
                }
                steps2.clear()
            }
        }

        println("Found $cycles cycles")
        val endT = Instant.now().toEpochMilli()

        println("Run for: ${endT - startT}ms")
        println("Additional printlns are about 10% overhead")
    }

    private fun resolve2b() {
        println("Part2b coroutines")
        val startT = Instant.now().toEpochMilli()
        val cycles = AtomicInteger(0)

        runBlocking(Dispatchers.Default) {
            for (x in map[0].indices) {
                for (y in map.indices) {
                    launch {
                        var direction = Vector.UP
                        val steps2 = hashMapOf<Point, MutableSet<Vector>>()
                        val poi = Point(x, y)
                        direction = Vector.UP
                        var point = start

                        try {
                            while (true) {
                                point = point.move(direction)
//                                if (point == poi) println("on obstacle")
                                if (map.get(point) != '#' && point != poi) {
                                    if (steps2.contains(point)) {
                                        val had = steps2[point]!!.add(direction)
                                        if (!had) {
                                            cycles.incrementAndGet()
                                            throw Exception("Found cycle")
                                        }
                                    } else {
                                        steps2[point] = mutableSetOf(direction)
                                    }
                                } else {
                                    point = point.moveBack(direction)
                                    direction = direction.rotate()
//                            println("Going $direction")
                                }
                            }
                        } catch (t: Throwable) {
//                            println("done walking $poi")
                        }
                        steps2.clear()
                    }
                }
            }
        }

        println("Found $cycles cycles")
        val endT = Instant.now().toEpochMilli()

        println("Run for: ${endT - startT}ms")
        println("Additional printlns are about 10% overhead")
    }

    private data class Point(val x: Int, val y: Int) {

        fun move(vector: Vector) = Point(this.x + vector.x, this.y + vector.y)
        fun moveBack(vector: Vector) = Point(this.x - vector.x, this.y - vector.y)
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