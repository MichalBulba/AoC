package twoFour

import java.io.File

object Three {
    fun resolve() {
//        resolve1()
        resolve2()
    }

    private fun resolve1() {
        var mul = 0
        File("24/input_three.txt").forEachLine { line ->
            Regex("mul\\((\\d*),(\\d*)\\)").findAll(line).forEach {
                val (a, b) = it.destructured
                val m = a.toInt()*b.toInt()
                println("$a $b")
                println(m)
                mul += m
            }
        }
        println("mul is $mul")
    }

    private fun resolve2() {
        var mul = 0
        var count = true
        File("24/input_three.txt").forEachLine { line ->
            Regex("mul\\((\\d*),(\\d*)\\)|(do\\(\\))|(don\\'t\\(\\))").findAll(line).forEach {
                val (a, b, doo, dont) = it.destructured
                val aa = if (a.isNotEmpty()) a.toInt() else 0
                val bb = if (b.isNotEmpty()) b.toInt() else 0
                val dooo = doo.isNotEmpty()
                val dontt = dont.isNotEmpty()
                println("$a $b $doo $dont")
                if(dooo) {
                    count = true
                } else if (dontt) {
                    count = false
                }
                if(count) {
                    mul += aa * bb
                }
            }
        }
        println("mul is $mul")
    }
}