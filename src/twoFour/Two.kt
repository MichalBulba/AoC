package twoFour

import java.io.File

object Two {

    val TOP = 3
    fun resolve() {
//        resolve1()
        resolve2a()
    }

    private fun resolve1() {
        var safe = 0
        File("24/input_two.txt").forEachLine { line ->
            val levels = line.split(" ").map { it.toInt() }
            val size = levels.size

            val gradients = (0..size-2).map { levels.gradient(it) }
            val sUp = gradients.all { it in 1..TOP}
            val sDown = gradients.all { it in -TOP..-1}
            val s = sUp || sDown
            println(levels)
            println(gradients)
            gradients.forEach {
                val sUp =  it in 1..TOP
                val sDown = it in -TOP..-1
                println("$sUp $sDown")
            }
            println("Is safe: $s")
            println()
            if(s) {
                safe++
            }
        }
        println("Safe reports are $safe")
    }

    private fun resolve2() {
        //263 is bad, 275 is bad
        var safe = 0
        File("24/input_two.txt").forEachLine { line ->
            val levels = line.split(" ").map { it.toInt() }
            println(levels)

            val gradients = (0..levels.size-2).map { levels.gradient(it) }.toMutableList()
            var bad = gradients.indexOfFirst { it == 0 || it > TOP || it < -TOP }

            val fixed: MutableList<Int> = levels.toMutableList()
            if(bad >= 0) {
                if(bad == gradients.size-1) bad++
                fixed.removeAt(bad)
                println("fixing1")
                println(fixed)
            }

            val gradientsFixed = (0..fixed.size-2).map { fixed.gradient(it) }
            val sUp = gradientsFixed.all { it in 1..TOP}
            val sDown = gradientsFixed.all { it in -TOP..-1}
            val s = sUp || sDown
            if(s) {
                safe++
                if(bad >= 0) {
                    println(levels)
                    println(gradients)
                    println(fixed)
                    println(gradientsFixed)
                    println("Fixed1")
                    println()
                }
            } else {
                val group: Map<Boolean, List<Int>> = gradients.groupBy { it > 0 }
                val grad = (group[true]?.size ?: 0) > (group[false]?.size ?: 0)
                val fixed: MutableList<Int> = levels.toMutableList()
                if(grad) {
                    // up
                    var bad = gradients.indexOfFirst { it < 0 }
                    if(bad == gradients.size-1) bad++
                    if (bad >= 0) {
                        fixed.removeAt(bad)
                        println("fixing2.1")
                    }
                } else {
                    // down
                    var bad = gradients.indexOfFirst { it > 0 }
                    if(bad == gradients.size-1) bad++
                    if (bad >= 0) {
                        fixed.removeAt(bad)
                        println("fixing2.2")
                    }
                }

                val gradientsFixed = (0..fixed.size-2).map { fixed.gradient(it) }
                val sUp = gradientsFixed.all { it in 1..TOP}
                val sDown = gradientsFixed.all { it in -TOP..-1}
                val s = sUp || sDown

                println(levels)
                println(gradients)
                println(fixed)
                println(gradientsFixed)

                if(s) {
                    println("Fixed2")
                    safe++
                }
                println()
            }


        }
        println("Safe reports are $safe")
    }
    private fun resolve2a() {
        var safe = 0
        File("24/input_two.txt").forEachLine { line ->
            val levels = line.split(" ").map { it.toInt() }
            println(levels)
            val gradients = (0..levels.size-2).map { levels.gradient(it) }
            val bad = gradients.indexOfFirst { it == 0 || it > TOP || it < -TOP }
            var fixedInOne = false
            if(bad == -1) {
                val sUp = gradients.all { it in 1..TOP}
                val sDown = gradients.all { it in -TOP..-1}
                val s = sUp || sDown
                if (s) {
                    safe++
                    println("$levels: safe")
                    fixedInOne = true
                }
            }
            if(!fixedInOne) {
                var fixedBoolean = false
                levels.forEachIndexed { i, it ->
                    val fixed = levels.toMutableList()
                    fixed.removeAt(i)
                    println(fixed)
                    val gradients = (0..fixed.size-2).map { fixed.gradient(it) }
                    println(gradients)
                    val bad = gradients.indexOfFirst { it == 0 || it > TOP || it < -TOP }
                    if(bad == -1) {
                        val sUp = gradients.all { it in 1..TOP}
                        val sDown = gradients.all { it in -TOP..-1}
                        val s = sUp || sDown
                        if(s) {
                            fixedBoolean = true
                        }
                    }
                }
                if(fixedBoolean) {
                    safe++
                    println("$levels: fixed")
                } else {
                    println("$levels: no hope")
                }

            }
        }
        println("Safe reports are $safe")
    }

    private fun List<Int>.gradient(i: Int) = this[i+1] - this[i]
}