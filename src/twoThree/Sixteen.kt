package twoThree

import java.io.File

object Sixteen {

    private val lines = File("23/input_sixteen.txt").readLines()
    private val items = lines.mapIndexed { y, it ->  it.toCharArray().mapIndexed { x, c -> c.toItem(x, y) } }
    init {

    }

    fun resolve() {
        resolve1()
//        resolve2()
    }

    private fun resolve1() {
        var prev: Pair<Int, Int> = -1 to 0
        var nexts: List<Pair<Int, Int>> = listOf(0 to 0)

        val energized = navigate(nexts, prev)

        println("Energized: ${energized.size}")
    }

    private fun resolve2() {

    }

    private fun navigate(nexts: List<Pair<Int, Int>>, prev: Pair<Int, Int> ): Set<Item> {
        val energized = nexts.filter { it.first in items.first().indices && it.second in items.indices }.map { items[it.second][it.first] }.toSet()
        return if(energized.isNotEmpty()) {
            val hackyPrev = if (prev.first < 0 || prev.second < 0) Item.Empty(prev.first, prev.second) else items[prev.second][prev.first]
            val energizezed: Set<Item> = nexts.flatMap { navigate(items[it.second][it.first].nextXY(hackyPrev), it) }.toSet()
            energized + energizezed
        } else {
            energized
        }
    }

    fun Char.toItem(x: Int, y: Int) = when(this) {
        '.' -> Item.Empty(x, y)
        '\\' -> Item.Left(x, y)
        '/' -> Item.Right(x, y)
        '|' -> Item.Straight(x, y)
        '-' -> Item.Horizontal(x, y)
        else -> throw IllegalArgumentException("Unknown item")
    }
    sealed class Item {

        abstract fun nextXY(prev: Item): List<Pair<Int, Int>>

        abstract val x: Int
        abstract val y: Int

        data class Right(override val x: Int, override val y: Int) : Item() {
            override fun nextXY(prev: Item): List<Pair<Int, Int>> {
                val direction = prev.direction(this)
                return when (direction) {
                    Direction.Up -> this.copy(x = x+1)
                    Direction.Down -> this.copy(x = x-1)
                    Direction.Left -> this.copy(y = y+1)
                    Direction.Right -> this.copy(y = y-1)
                }.let { listOf(it.x to it.y) }
            }

        }

        data class Left(override val x: Int, override val y: Int) : Item() {
            override fun nextXY(prev: Item): List<Pair<Int, Int>> {
                val direction = prev.direction(this)
                return when (direction) {
                    Direction.Up -> this.copy(x = x-1)
                    Direction.Down -> this.copy(x = x+1)
                    Direction.Left -> this.copy(y = y-1)
                    Direction.Right -> this.copy(y = y+1)
                }.let { listOf(it.x to it.y) }
            }

        }
        data class Straight(override val x: Int, override val y: Int) : Item() {
            override fun nextXY(prev: Item): List<Pair<Int, Int>> {
                val direction = prev.direction(this)
                return when(direction) {
                    Direction.Up -> listOf(this.copy(y = y-1))
                    Direction.Down -> listOf(this.copy(y = y+1))
                    Direction.Left, Direction.Right -> listOf(this.copy(y = y-1), this.copy(y = y+1))
                }.map { it.x to it.y }
            }

        }

        data class Horizontal(override val x: Int, override val y: Int) : Item() {
            override fun nextXY(prev: Item): List<Pair<Int, Int>> {
                val direction = prev.direction(this)
                return when(direction) {
                    Direction.Up, Direction.Down -> listOf(this.copy(x = x-1), this.copy(x = x+1))
                    Direction.Left -> listOf(this.copy(x = x-1))
                    Direction.Right -> listOf(this.copy(x = x+1))
                }.map { it.x to it.y }
            }

        }

        data class Empty(override val x: Int, override val y: Int) : Item() {
            override fun nextXY(prev: Item): List<Pair<Int, Int>> {
                val direction = prev.direction(this)
                return when (direction) {
                    Direction.Up -> this.copy(y = y-1)
                    Direction.Down -> this.copy(y = y+1)
                    Direction.Left -> this.copy(x = x-1)
                    Direction.Right -> this.copy(x = x+1)
                }.let { listOf(it.x to it.y) }
            }

        }
    }

    fun Item.direction(next: Item) = when {
            x == x && y < next.y -> Direction.Down
            x == x && y > next.y -> Direction.Up
            y == y && x < next.x -> Direction.Right
            y == y && x > next.x -> Direction.Left
            else -> throw IllegalArgumentException("Unknown direction")
        }

    enum class Direction {
        Up, Down, Left, Right
    }
}


