package twoFour

import java.io.File

object Four {
    fun resolve() {
        resolve1()
        resolve2()
    }

    private val XMAS = "XMAS"
    private val SAMX = "SAMX"

    private val R_XMAS = Regex(XMAS)
    private val R_SAMX = Regex(SAMX)

    val lines = mutableListOf<String>()
    val linesV = mutableListOf<String>()

    val diag = mutableListOf<String>()
    val diagF = mutableListOf<String>()
    val diagV = mutableListOf<String>()
    val diagVF = mutableListOf<String>()

    private fun resolve1() {
        File("24/input_four.txt").forEachLine { line ->
            lines.add(line)
        }

        val x = lines.first().length
        val y = lines.size

        val sb = StringBuilder()
        (0 until x).forEach { x ->
            lines.forEach {
                sb.append(it.elementAt(x))
            }
            linesV.add(sb.toString())
            sb.clear()
        }

        topLeftHalfDiag()
        bottomLeftHalfDiag()
        topRightHalfDiag()
        bottomRightHalfDiag()

        var sum = 0
        sum += lines.findXmas()
        sum += linesV.findXmas()
        sum += diag.findXmas()
        sum += diagF.findXmas()
        sum += diagV.findXmas()
        sum += diagVF.findXmas()
        println("Found ${diag.size + diagF.size + diagV.size + diagVF.size} lines")
        println("Part 1 sum is $sum")
    }

    private fun List<String>.findXmas() = this.sumOf {
        R_XMAS.findAll(it).toList().size + R_SAMX.findAll(it).toList().size
    }

    private fun topLeftHalfDiag() {
        val tl = Point(0, 0)
        val start = tl
        (0 until lines.first().length).forEach { i ->
            var point = start.copy(y = start.y + i)
            val sb = StringBuilder()
            do {
                sb.append(lines[point.x].elementAt(point.y))
                point = point.copy(x = point.x + 1, y = point.y - 1)
            } while (point.y >= 0)
            diag.add(sb.toString())
        }
    }

    private fun topRightHalfDiag() {
        val tr = Point(lines.size-1, 0)
        val start = tr
        (0 until lines.first().length).forEach { i ->
            var point = start.copy(y = start.y + i)
            val sb = StringBuilder()
            do {
//                println(point)
                sb.append(lines[point.x].elementAt(point.y))
                point = point.copy(x = point.x - 1, y = point.y - 1)
            } while (point.y >= 0)
//            println()
            diagF.add(sb.toString())
        }
        diagF.removeLast()
    }

    private fun bottomRightHalfDiag() {
        val bl = Point(0, lines.size-1)
        val start = bl
        (0 until lines.first().length).forEach { i ->
            var point = start.copy(y = start.y - i)
            val sb = StringBuilder()
            do {
//                println(point)
                sb.append(lines[point.x].elementAt(point.y))
                point = point.copy(x = point.x + 1, y = point.y + 1)
            } while (point.y <= lines.first().length-1)
//            println()
            diagV.add(sb.toString())
        }
    }

    private fun bottomLeftHalfDiag() {
        val br = Point(lines.size-1, lines.size-1)
        val start = br
        (0 until lines.first().length).forEach { i ->
            var point = start.copy(y = start.y - i)
            val sb = StringBuilder()
            do {
//                println(point)
                sb.append(lines[point.x].elementAt(point.y))
                point = point.copy(x = point.x - 1, y = point.y + 1)
            } while (point.y <= lines.first().length-1)
//            println()
            diagVF.add(sb.toString())
        }
        diagVF.removeLast()
    }

    private fun resolve2() {
        var sum = 0
        (1 until lines.size-1).forEach { x ->
            (1 until linesV.size-1).forEach { y ->
                if(lines.at(x, y) == "A") {
                    var one = false
                    if(lines.at(x+1, y+1) == "S" && lines.at(x-1, y-1) == "M") one = true
                    if(lines.at(x+1, y+1) == "M" && lines.at(x-1, y-1) == "S") one = true

                    var two = false
                    if(lines.at(x-1, y+1) == "S" && lines.at(x+1, y-1) == "M") two = true
                    if(lines.at(x-1, y+1) == "M" && lines.at(x+1, y-1) == "S") two = true

                    if(one && two) sum++
                }
            }
        }

        println("Sum of MAS: $sum")
    }

    private fun List<String>.at(x: Int, y: Int) = this[y].elementAt(x).toString()

    private data class Point(val x: Int, val y: Int) {
        override fun toString(): String {
            return "[$x, $y]"
        }
    }
}