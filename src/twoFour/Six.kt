package twoFour

import java.io.File

object Six {

    private val map = mutableListOf<String>()
    private var point = Point(0, 0)
    private var direction = Vector.UP
    private val steps = mutableSetOf<Point>()

    private fun List<String>.get(x: Int, y: Int) = map[y].elementAt(x)
    private fun List<String>.get(point: Point) = map[point.y].elementAt(point.x)

    fun resolve() {
        resolve1()
//        resolve2()
    }

    private fun resolve1() {

        File("24/input_six.txt").forEachLine { line ->
            map += line
            val x = line.indexOf('^')
            if(x > 0) {
                point = Point(x, map.size)
            }
        }

        try {
            steps += point
            println("Going $direction")
            while (true) {
                point = point.move(direction)
                if(map.get(point) != '#') {
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
    }

    private fun resolve2() {
        File("24/input_six.txt").forEachLine { line ->

        }
        println("")
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
        LEFT(-1,0 ) {
            override fun rotate() = UP

        },
        RIGHT(1, 0) {
            override fun rotate() = DOWN

        };
        abstract fun rotate(): Vector

    }

}