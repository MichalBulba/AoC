package twoThree

import java.io.File

object Eighteen {

    private val lines = File("23/input_eighteen.txt").readLines()
    private val items = lines.map { line ->
        val(d, n,  c) = line.split(" ")
        d.toCharArray().first().toItem(n.toInt(), c)
    }
    init {

    }

    fun resolve() {
        resolve1()
//        resolve2()
    }

    private fun resolve1() {
        val output = MutableList(1000) { mutableListOf<Point>() }
        var prev = Point(500,500)

        items.forEach { item ->
            val nexts = item.next(prev)
            nexts.forEach { next->
                output[next.y].add(next)
            }
            prev = nexts.last()
        }
        output.forEach { line: MutableList<Point> ->
            line.sortBy { it.x }
        }

        val filtered = output.filter { it.isNotEmpty() }
//        filtered.forEach {
//            println(it)
//        }

        //too naive
//        val rows = filtered.map { out ->
//            out.last().x - out.first().x + 1
//        }
        var sum = 0
        val rows = filtered.map { row: MutableList<Point> ->
            var first = true
            var border = true
            for (i in 0..row.size-2) {
                if(row[i].x+1 == row[i+1].x ) {
                    border = false
                    if (first) sum += 1
                    sum += 1
                    first = false
                } else {
                    if (border)sum += row[i+1].x - row[i].x + 1
                    border != border
                    first = true
                }
            }
        }

        val map = MutableList(1000) { MutableList(1000) { '.' } }

        filtered.forEach {l: MutableList<Point> -> l.forEach { p: Point -> map[p.y][p.x] = '#' } }

        val filteredMap = map.filter { it.contains('#') }
        filteredMap.forEach {
            println(it)
        }

        println("Energized: $sum")
    }

    private fun resolve2() {

    }

    fun Char.toItem(distance:Int, color: String) = when(this) {
        'D' -> Item.Down(distance, color)
        'U' -> Item.Up(distance, color)
        'L' -> Item.Left(distance, color)
        'R' -> Item.Right(distance, color)
        else -> throw IllegalArgumentException("Unknown item")
    }
    sealed class Item {

        abstract fun next(prev: Point): List<Point>

        abstract val number: Int
        abstract val color: String

        data class Right(override val number: Int, override val color: String) : Item() {
            override fun next(prev: Point): List<Point> {
                val nexts = mutableListOf<Point>()
                for(i in 1..number) {
                    nexts.add(Point(prev.x+i, prev.y))
                }
                return nexts.toList()
            }

        }

        data class Left(override val number: Int, override val color: String) : Item() {
            override fun next(prev: Point): List<Point> {
                val nexts = mutableListOf<Point>()
                for(i in 1 .. number) {
                    nexts.add(Point(prev.x-i, prev.y))
                }
                return nexts.toList()
            }

        }

        data class Down(override val number: Int, override val color: String) : Item() {
            override fun next(prev: Point): List<Point> {
                val nexts = mutableListOf<Point>()
                for(i in 1 .. number) {
                    nexts.add(Point(prev.x, prev.y+i))
                }
                return nexts.toList()
            }

        }

        data class Up(override val number: Int, override val color: String) : Item() {
            override fun next(prev: Point): List<Point> {
                val nexts = mutableListOf<Point>()
                for(i in 1 .. number) {
                    nexts.add(Point(prev.x, prev.y-i))
                }
                return nexts.toList()
            }

        }
    }

    data class Point(val x: Int, val y: Int)
}


