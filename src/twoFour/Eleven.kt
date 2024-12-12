package twoFour

import java.io.File
import java.time.Instant
import kotlin.system.measureTimeMillis

object Eleven {

    private var stones = listOf<String>()
    private var iter = 0

    private val cache = hashMapOf<String, List<String>>()
    private val cache2 = hashMapOf<String, ArrayDeque<String>>()

    fun resolve() {
        resolve1()
        resolve4()
        //quite the struggle to get part2 one right
    }

    private fun resolve1() {
        val startT = Instant.now().toEpochMilli()
        File("24/input_eleven.txt").forEachLine { line ->
            stones = line.split(" ")
        }

        var stonesM = stones.toMutableList()
        repeat(25) {
            stonesM = blink2(stonesM).toMutableList()
        }

        val endT = Instant.now().toEpochMilli()
        println("Run for: ${endT - startT}ms")
        println("Have ${stonesM.size} stones")
    }

    private fun blink(stones: List<String>) = buildList<String> {
            stones.forEach { stone ->
                if (stone == "0") {
                    add("1")
                } else if (stone.length % 2 == 0) {
                    val half = stone.length / 2
                    val l = stone.take(half).toLong().toString()
                    val r = stone.takeLast(half).toLong().toString()
                    add(l)
                    add(r)
                } else {
                    val new = (stone.toLong() * 2024).toString()
                    add(new)
                }
            }
        }

    private fun blink2(stones: List<String>) = stones.flatMap { blink(it) }

    private fun blink(stone: String): List<String> = cache.getOrElse(stone) {
        if (stone == "0") {
            cache[stone] = listOf("1")
            listOf("1")
        } else if (stone.length % 2 == 0) {
            val half = stone.length / 2
            val l = stone.take(half).toLong().toString()
            val r = stone.takeLast(half).toLong().toString()
            cache[stone] = listOf(l, r)
            listOf(l, r)
        } else {
            val new = (stone.toLong() * 2024).toString()
            cache[stone] = listOf(new)
            listOf(new)
        }
    }

    private fun resolve2() {
        println("Part2")
        val startT = Instant.now().toEpochMilli()

        var lim = 1
        var stonesM = stones.toMutableList()
        while (lim < 50) {
            val run = measureTimeMillis {
                blinkR(stonesM, 1, lim)
                print("$lim: $iter")
                iter = 0
                lim++
            }
            println(" ${run/1000}")

            println("Cache size: ${cache.size}")
        }
        println("main count")
        val sum = blinkR(stones, 1, 75)

        val endT = Instant.now().toEpochMilli()
        println("Run for: ${endT - startT}ms")
        println("Have $sum stones")
    }

    private fun resolve4() {
        println("Part2")
        val startT = Instant.now().toEpochMilli()

        val blinks = 75
        val cache = 25

        var roll = true
        while (roll) {
            roll = addToCache(stones, cache)
            println("Cache size: ${cache2.size}")
        }

        roll = true
        while (roll) {
            roll = addToCache(cache2.values.flatten().distinct(), cache)
            println("Cache size: ${cache2.size}")
        }

        println("Cache ready")

        val iterations = blinks / cache
        val sumR = blinkRCached(stones, 1, iterations)

        val endT = Instant.now().toEpochMilli()
        println("Run for: ${endT - startT}ms")
        println("Have $sumR stones")

    }

    private fun addToCache(stones: List<String>, cache: Int): Boolean {
        var did = false
        stones.forEach { stone ->
            if(!cache2.containsKey(stone)) {
                var stonesM = mutableListOf(stone)
                repeat(cache) {
                    stonesM = blink2(stonesM).toMutableList()
                }
                cache2[stone] = ArrayDeque(stonesM)
                did = true
            }
        }
        return did
    }

    private fun blinkR(stones: List<String>, i: Int, lim: Int): Int {
        if (i <= lim) { return stones.sumOf { blinkR(blink(listOf(it)), i + 1, lim) } }
        return stones.size
    }

    private fun blinkRCached(stones: List<String>, i: Int, lim: Int): Long {
        if (i <= lim) { return stones.sumOf { blinkRCached(cache2[it]!!, i + 1, lim) } }
        return stones.size.toLong()
    }
}
