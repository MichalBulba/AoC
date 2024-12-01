package twoThree

import java.io.File
import kotlin.math.min

object Thirteen {

    private val lines = File("23/input_thirteen.txt").readLines()
    private var cols = listOf<String>()

    private var patterns = mutableListOf<Pattern>()

    init {
        val temp = mutableListOf<String>()
        lines.forEach {
            if(it.isNotEmpty()) {
                temp += it
            } else {
                patterns += toPattern(temp.toList())
                temp.clear()
            }
        }
    }

    fun resolve() {
//        resolve1()
        resolve2()
    }

    private fun resolve1() {
        var sum = 0
        var gRow = 0
        var gCol = 0
        patterns.forEachIndexed { i, pattern ->
            val prevSum = sum
            var row = -1
            for (y in 0..pattern.rows.size - 2) {
                if (pattern.rows[y] == pattern.rows[y + 1] && verifyCandidateLine(y, pattern.rows)) {
                    row = y
                    print("row $i $y ")
                    println(sum)
                    sum += (y+1)*100
                }
            }

            var col = -1
            for (x in 0..pattern.cols.size - 2) {
                if (pattern.cols[x] == pattern.cols[x + 1] && verifyCandidateCol(x, pattern.cols)) {
                    col = x
                    print("col $i $x ")
                    println(sum)
                    sum += (x+1)
                }
            }
            println(sum - prevSum)

            row += 1
            col += 1
            if (row <= 0 && col <= 0) println("potential bug at $i none")
            if (row > 0 && col > 0) println("potential bug at $i both")
//            sum += row*100 + col
        }
        println()
        println("Row*100 plus col: $sum")
    }

    private fun resolve2() {
        var sum = 0
        var gRow = 0
        var gCol = 0
        patterns.forEachIndexed { i, pattern ->
            val prevSum = sum
            var row = -1
            for (y in 0..pattern.rows.size - 2) {
                if (pattern.rows[y] == pattern.rows[y + 1] && verifyCandidateLine2(y, pattern.rows)) {
                    row = y
                    print("row $i $y ")
                    println(sum)
                    sum += (y+1)*100
                }
            }
            if(row == -1) {
                for (y in 0..pattern.rows.size - 2) {
                    if (exactlyOneDiffers( pattern.rows[y], pattern.rows[y + 1]) && verifyCandidateLine(y, pattern.rows)) {
                        row = y
                        print("row $i $y ")
                        println(sum)
                        sum += (y+1)*100
                    }
                }
            }

            var col = -1
            for (x in 0..pattern.cols.size - 2) {
                if (pattern.cols[x] == pattern.cols[x + 1] && verifyCandidateCol2(x, pattern.cols)) {
                    col = x
                    print("col $i $x ")
                    println(sum)
                    sum += (x+1)
                }
            }
            if(col == -1) {
                for (x in 0..pattern.cols.size - 2) {
                    if (exactlyOneDiffers(pattern.cols[x], pattern.cols[x + 1]) && verifyCandidateCol(x, pattern.cols)) {
                        col = x
                        print("col $i $x ")
                        println(sum)
                        sum += (x+1)
                    }
                }
            }

            println(sum - prevSum)

            row += 1
            col += 1
            if (row <= 0 && col <= 0) println("potential bug at $i none")
            if (row > 0 && col > 0) println("potential bug at $i both")
//            sum += row*100 + col
        }
        println()
        println("Row*100 plus col fixed: $sum")
    }

    private fun exactlyOneDiffers(s: String, s1: String): Boolean {
        var diff = 0
        s.forEachIndexed { index, c ->
            if (c != s1[index]){
                diff++
            }
        }
        return diff == 1
    }

    private fun verifyCandidateLine(y: Int, rows: List<String>, e: Int = 0): Boolean {
        //y=7
        val dm = rows.size-1 - (y+1)
        val d = min(y, dm)
        return (0..d).map { di ->
            if(di==0) true
            else rows[y-di] == rows[y+1+di]
        }.count { !it } == e
    }

    private fun verifyCandidateCol(x: Int, cols: List<String>, e: Int = 0): Boolean {
        val dm = cols.size-1 - (x+1)
        val d = min(x, dm)
        return (0..d).map { di ->
            if(di==0) true
            else cols[x-di] == cols[x+1+di]
        }.count { !it } == e
    }

    private fun verifyCandidateLine2(y: Int, rows: List<String>, e: Int = 0): Boolean {
        //y=7
        val dm = rows.size-1 - (y+1)
        val d = min(y, dm)
        var diffs = 0
        (0..d).forEach { di ->
            if (exactlyOneDiffers(rows[y - di], rows[y + 1 + di])) {
                diffs++
            } else if(rows[y-di] != rows[y+1+di]) {
                return false
            }
        }
        return diffs == 1
    }

    private fun verifyCandidateCol2(x: Int, cols: List<String>, e: Int = 0): Boolean {
        val dm = cols.size-1 - (x+1)
        val d = min(x, dm)
        var diffs = 0
        (0..d).forEach { di ->
            if (exactlyOneDiffers(cols[x-di],cols[x+1+di])) {
                diffs++
            } else if (cols[x-di] != cols[x+1+di]) {
                return false
            }
        }
        return diffs == 1
    }

    data class Pattern(val rows: List<String>, val cols: List<String>)

    fun toPattern(lines: List<String>) : Pattern {
        val colss = mutableListOf<String>()
        lines.first().forEachIndexed { x, c ->
            val sb = StringBuilder(lines.size)
            lines.forEachIndexed { y, line ->
                sb.append(line[x])
            }
            colss += sb.toString()
        }
        return Pattern(lines.toList(), colss)
    }
}


