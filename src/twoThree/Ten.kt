package twoThree

import twoThree.Ten.Tile.*
import java.io.File

object Ten {

    /**
     * | is a vertical pipe connecting north and south.
     * - is a horizontal pipe connecting east and west.
     * L is a 90-degree bend connecting north and east.
     * J is a 90-degree bend connecting north and west.
     * 7 is a 90-degree bend connecting south and west.
     * F is a 90-degree bend connecting south and east.
     * . is ground; there is no pipe in this tile.
     * S is the starting position of the animal; there is a pipe on this tile, but your sketch doesn't show what shape the pipe has.
     */

    private val lines = File("23/input_ten.txt").readLines()
    private var start: Start = Start(0, 0)
    private val tiles: List<List<Tile>> = lines.mapIndexed { y, line ->
        line.toCharArray().mapIndexed { x, it ->
            val tile = toTile(it, x, y)
            if (tile is Start) {
                start = tile
            }
            tile
        }
    }

    fun resolve() {
        resolve1()
        resolve2()
    }

    private fun resolve1() {
        println("Start is ${lines[start.y][start.x]}")
        var path = 1
        val next = start.next().first()

        var prev: Tile = start
        var valid = next

        val valids = mutableListOf<Tile>()
        while (valid != start && (valid !is Ground) && path < 100000) {
            valids.add(valid)
            println("Valid is: ${tiles[valid.y][valid.x]}")
            path++
            val nexts = valid.next()
            val tPrev = prev
            prev = valid
            valid = nexts.first { it != tPrev }
        }
        println("Finished on $valid")


        println("Path: $path")
        println("Distance: ${path/2}")


        //draw map
        tiles.forEach {line ->
            line.forEach {
                if(valids.contains(it)) {
                    print("\u001B[31m${lines[it.y][it.x]}\u001B[0m")
                } else {
                    print(lines[it.y][it.x])
            }
            }
            println()
        }
    }

    private fun resolve2() {

        println("Start is ${lines[start.y][start.x]}")
        var path = 1
        val next = start.next().first()

        var prev: Tile = start
        var valid = next

        val valids = mutableListOf<Tile>()
        while (valid != start && (valid !is Ground) && path < 100000) {
            valids.add(valid)
            println("Valid is: ${tiles[valid.y][valid.x]}")
            path++
            val nexts = valid.next()
            val tPrev = prev
            prev = valid
            valid = nexts.first { it != tPrev }
        }
        println("Finished on $valid")


        println("Path: $path")
        println("Distance: ${path/2}")


        //draw map
        tiles.forEach {line ->
            line.forEach {
                if(valids.contains(it)) {
                    print("\u001B[31m${lines[it.y][it.x]}\u001B[0m")
                } else {
                    print(lines[it.y][it.x])
                }
            }
            println()
        }
//        println("14, 24 -> 14,25 are connected? ${checkIfConnected(tiles[14][24], tiles[14][25])}")

        val dots: List<Tile> = tiles.flatten().filterIsInstance<Ground>()
        println(dots)
        val touching = dots.associateWith { allNeighboursHacked(it) }
        val touchingValids = touching.filter { it.value.isNotEmpty() && it.value.all { tile -> valids.contains(tile) || tile is Ground} }
        val connected = touchingValids.values.associateWith { list ->
            var allConnected = true
            val rounded = list + list.first()
            rounded.forEachIndexed { i, it ->
                if(i < rounded.size - 1) {
                    if (!checkIfConnected(it, rounded[i+1])) {
                        allConnected = false
                    }
                }
            }
            allConnected
        }
//        println(connected)
        println(connected.filter { it.value })

        //draw map
        tiles.forEach {line ->
            line.forEach {
                if(valids.contains(it)) {
                    print("\u001B[31m${lines[it.y][it.x]}\u001B[0m")
                } else if (touching.contains(it)) {
                    print("\u001B[32m.\u001B[0m")
                } else {
                    print(lines[it.y][it.x])
                }
            }
            println()
        }
    }
    //14, 24 -> 14,25
    private fun checkIfConnected(a: Tile, b: Tile): Boolean {
        println("Check if connected ")
        print(" Start is ${lines[a.y][a.x]}")
        var path = 1
        val next = a.next().firstOrNull() ?: return false

        var prev: Tile = a
        var valid = next

        while (valid != b && valid != a && (valid !is Ground)) {
//            print(" Valid is: ${tiles[valid.y][valid.x]}")
            path++
            val nexts = valid.next()
            val tPrev = prev
            prev = valid
            valid = nexts.first { it != tPrev }
        }
//        print(" finished on $valid")


//        print(" ath: $path")
//        print(" distance: ${path/2}")
        println()
        return valid == b
    }

    private fun allNeighboursHacked(tile: Tile): List<Tile> {
        val x = tile.x
        val y = tile.y
        return if (x > 0 && y > 0 && y < tiles.size-1 && x < tiles.first().size-1) {
            listOf(
                    tiles[y-1][x],
                    tiles[y-1][x+1],
                    tiles[y][x+1],
                    tiles[y+1][x+1],
                    tiles[y+1][x],
                    tiles[y+1][x-1],
                    tiles[y][x-1],
                    tiles[y-1][x-1],
            )
        } else {
            emptyList()
        }
    }

    sealed class Tile {
        abstract val x: Int
        abstract val y: Int

        abstract fun next(): List<Tile>

        data class Start(override val x: Int, override val y: Int): Tile() {
            override fun next(): List<Tile> {
               return listOf(
                       //big
                       tiles[y][x-1],
                       tiles[y][x+1]
                       //small
//                       tiles[y+1][x],
//                       tiles[y][x+1]
                       //medium
//                       tiles[y][x+1],
//                       tiles[y+1][x],
               )
            }
        }

        data class Vertical(override val x: Int, override val y: Int): Tile() {
            override fun next(): List<Tile> {
                return listOf(tiles[y-1][x], tiles[y+1][x])
            }
        }
        data class Horizontal(override val x: Int, override val y: Int): Tile() {
            override fun next(): List<Tile> {
                return listOf(tiles[y][x-1], tiles[y][x+1])
            }
        }
        data class TopRight(override val x: Int, override val y: Int): Tile() {
            override fun next(): List<Tile> {
                return listOf(tiles[y-1][x], tiles[y][x+1])
            }
        }
        data class TopLeft(override val x: Int, override val y: Int): Tile() {
            override fun next(): List<Tile> {
                return listOf(tiles[y-1][x], tiles[y][x-1])
            }
        }
        data class DownLeft(override val x: Int, override val y: Int): Tile() {
            override fun next(): List<Tile> {
                return listOf(tiles[y+1][x], tiles[y][x-1])
            }
        }
        data class DownRight(override val x: Int, override val y: Int): Tile() {
            override fun next(): List<Tile> {
                return listOf(tiles[y+1][x], tiles[y][x+1])
            }
        }
        data class Ground(override val x: Int, override val y: Int): Tile() {
            override fun next(): List<Tile> {
                return listOf()
            }
        }
    }

    private fun toTile(c: Char, x: Int, y: Int): Tile = when(c) {
        '|' -> Vertical(x, y)
        '-' -> Horizontal(x, y)
        'L' -> TopRight(x, y)
        'J' -> TopLeft(x, y)
        '7' -> DownLeft(x, y)
        'F' -> DownRight(x, y)
        '.' -> Ground(x, y)
        'S' -> Start(x, y)
        else -> throw IllegalArgumentException("Unknown tile")
    }
}


