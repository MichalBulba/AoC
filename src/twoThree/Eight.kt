package twoThree

import util.Utils
import java.io.File

object Eight {
    private var directions = ""
    private val map = mutableMapOf<String, Mapp>()
    private val lines = File("23/input_eight.txt").readLines().mapIndexed { i, line ->
        if (i == 0) directions = line
        else if (i > 1) {
            //BQV = (HFG, GDR)
            val splitEquals = line.split("=")
            val from = splitEquals.first().trim()
            val (left, right) = splitEquals[1].trim().replace("(", "").replace(")", "").split(", ")
            map[from] = Mapp(left, right)
        }
    }

    fun resolve() {
//        resolve1()
        resolve2clever()
    }

    private fun resolve1() {
        var next = "AAA"
        var count = 0
        var i = 0
        while (next != "ZZZ") {
            i++
            for (direction: Char in directions) {
                val mapp = map[next]
                val go = if (direction == 'R') mapp?.right else mapp?.left
                next = go!!
                count++
                if (next == "ZZZ") break
            }
        }
        println("ended up at: $next after $i iterations")
        println("Steps: $count")
    }

    private fun resolve2naive() {
        var nexts = map.keys.filter { it.endsWith("A") }
        val ends = map.keys.filter { it.endsWith("Z") }
        var count = 0
        var i = 0L
        println(nexts)
        println(ends)
        while (nexts.any { !it.endsWith("Z") }) {
            i++
            for (direction: Char in directions) {
                nexts = nexts.map { next ->
                    val mapp = map[next]
                    val go = if (direction == 'R') mapp?.right else mapp?.left
                    go!!
                }

                count++
//                println(nexts)
                if (nexts.all { it.endsWith("Z") }){
                    break
                }
            }
            if (i % 1000000 == 0L)println(i)
        }

        println("ended up at: $nexts after $i iterations")
        println("Steps ghosted: $count")
    }

    private fun resolve2clever() {
        var nexts = map.keys.filter { it.endsWith("A") }
        val ends = map.keys.filter { it.endsWith("Z") }
        println(nexts)
        println(ends)

        var i = 0

        var where = nexts.map {
            var next = it
            var count = 0L
            while (!next.endsWith('Z')) {
                for (direction: Char in directions) {
                    val mapp = map[next]
                    val go = if (direction == 'R') mapp?.right else mapp?.left
                    next = go!!
                    count++
                    if (next.endsWith('Z')) break
                }
            }
            count
        }

        println(where)

        val NWW = where.reduce { acc, i ->
            val NWW = Utils.calculateNWW(acc, i)
            println(NWW)
            NWW
        }

        println("Steps ghosted: $NWW")
//        println("Steps ghosted: ${Utils.calculateNWW(*where.toLIntArray())}")
    }

    private data class Mapp(val left: String, val right: String)
}