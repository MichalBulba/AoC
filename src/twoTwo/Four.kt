package twoTwo

import java.io.File

object Four {
    fun resolve() {
        resolve1()
        resolve2()
    }

    private fun resolve1() {
        var pairs = 0
        val regex = "(\\d*)-(\\d*),(\\d*)-(\\d*)".toRegex()

        File("22/input_four.txt").forEachLine {
            val result = regex.find(it)
            val (s1, e1, s2, e2) = result!!.destructured
            val first = IntRange(s1.toInt(), e1.toInt())
            val second = IntRange(s2.toInt(), e2.toInt())
            val intersect = first.intersect(second)
            if(intersect == first.toSet() || intersect == second.toSet()) { pairs++ }
        }
        println("Fully overlapping pairs $pairs")
    }

    private fun resolve2() {
        var pairs = 0
        val regex = "(\\d*)-(\\d*),(\\d*)-(\\d*)".toRegex()

        File("22/input_four.txt").forEachLine {
            val result = regex.find(it)
            val (s1, e1, s2, e2) = result!!.destructured
            val first = IntRange(s1.toInt(), e1.toInt())
            val second = IntRange(s2.toInt(), e2.toInt())
            val intersect = first.intersect(second)
            if(intersect.isNotEmpty()) { pairs++ }
        }
        println("Any overlapping pairs $pairs")
    }
}