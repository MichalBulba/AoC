package twoThree

import java.io.File

object Three {

    private val lines = File("23/input_three.txt").readLines()
    private val lineLength = lines[0].length
    fun resolve() {
//        resolve1()
        resolve2()
    }

    private fun resolve1() {
        var allParts = 0
        lines.forEachIndexed { y, line ->
            var numberString = ""
            line.forEachIndexed { x, char ->
                if (char.isDigit()){
                    numberString += char
                }

                if ((!char.isDigit() ||  x == lineLength - 1) && numberString.isNotEmpty()) {
                    val number = numberString.toInt()
                    val adjustEnd = if (char.isDigit()) 1 else 0
                    //this is actual logic
                    if (hasSymbolAdjacent(x - numberString.length + adjustEnd, y, numberString.length)) {
                        allParts += number
                    }

                    numberString = ""
                }
            }
        }

        println("All part number is $allParts")
    }

    private fun resolve2() {
        val possibleGears = mutableMapOf<Star, MutableList<Int>>()
        lines.forEachIndexed { y, line ->
            var numberString = ""
            line.forEachIndexed { x, char ->
                if (char.isDigit()){
                    numberString += char
                }

                if ((!char.isDigit() ||  x == lineLength - 1) && numberString.isNotEmpty()) {
                    val number = numberString.toInt()
                    val adjustEnd = if (char.isDigit()) 1 else 0
                    //this is actual logic
                    getStarAdjacent(x - numberString.length + adjustEnd, y, numberString.length)?.let {
                        if(possibleGears.containsKey(it)) {
                            possibleGears[it]?.add(number)
                        } else {
                            possibleGears.put(it, mutableListOf(number))
                        }
                    }

                    numberString = ""
                }
            }
        }

        val gears = possibleGears.filter { it.value.size == 2 }
        val allGears = gears.values.sumOf { it[0] * it[1] }

        println("All gears number is $allGears")
    }

    private fun hasSymbolAdjacent(x: Int, y: Int, length: Int): Boolean {
        val l = if (x > 0) lines[y][x-1].toString() else "."
        val r = if (x+length < lineLength) lines[y][x+length].toString() else "."
        val t = if (y > 0) lines[y-1].substring(x, x+length) else "."
        val b = if (y < lines.size-1) lines[y+1].substring(x, x+length) else "."
        val tl = if(y > 0 && x > 0) lines[y-1][x-1].toString() else "."
        val bl = if(y < lines.size-1 && x > 0) lines[y+1][x-1].toString() else "."
        val tr = if(y > 0 && x+length < lineLength) lines[y-1][x+length].toString() else "."
        val br = if(y < lines.size-1 && x+length < lineLength) lines[y+1][x+length].toString() else "."

        val around = listOf(l, r, t, b, tl, bl, tr, br)
        val result = around.any { !it.all {c -> c == '.' } } //this is true condition

        if (around.any { it.toCharArray().all { c -> c.isDigit() } }){
            println("Digits around")
        }

        if (!result) {
            val lineT = if (y > 0) lines[y-1] else ""
            val lineS = lines[y]
            val lineB = if (y < lineLength-1) lines[y+1] else ""
            val i = 1
        }
        return result
    }

    private fun getStarAdjacent(x: Int, y: Int, length: Int): Star? {
        val l = if (x > 0) lines[y][x-1].toString() else "."
        val r = if (x+length < lineLength) lines[y][x+length].toString() else "."
        val t = if (y > 0) lines[y-1].substring(x, x+length) else "."
        val b = if (y < lines.size-1) lines[y+1].substring(x, x+length) else "."
        val tl = if(y > 0 && x > 0) lines[y-1][x-1].toString() else "."
        val bl = if(y < lines.size-1 && x > 0) lines[y+1][x-1].toString() else "."
        val tr = if(y > 0 && x+length < lineLength) lines[y-1][x+length].toString() else "."
        val br = if(y < lines.size-1 && x+length < lineLength) lines[y+1][x+length].toString() else "."

        val around = listOf(l, r, t, b, tl, bl, tr, br)
//        val result = around.any { it.star()  } //this is true condition
//        return if (result) {
           return when {
                l.star() -> Star(x-1, y)
                r.star() -> Star(x+length, y)
                tl.star() -> Star(x-1, y-1)
                bl.star() -> Star(x-1, y+1)
                tr.star() -> Star(x+length, y-1)
                br.star() -> Star(x+length, y+1)
                t.star() -> Star(x+t.indexOf("*"), y-1)
                b.star() -> Star(x+b.indexOf("*"), y+1)
                else -> null
            }
//        } else null
    }

    private data class Star(val x: Int, val y: Int)

    private fun String.star() = contains("*")
}