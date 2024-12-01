package twoThree

import java.io.File

object One {

    private val numbers = mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9"
    )

    fun resolve() {
        resolve1()
        resolve2()
    }

    private fun resolve1() {
        var calibration = 0
        File("23/input_one.txt").forEachLine {line ->
            val first = Integer.parseInt(line.first { char -> char in '1'..'9' }.toString())
            val last = Integer.parseInt(line.last { char -> char in '1'..'9' }.toString())
            calibration += ( first * 10 + last )
        }
        println("Calibration is: $calibration")
    }

    private fun resolve2() {
        var calibration = 0
        File("23/input_one.txt").forEachLine {lineO ->
            var line = lineO
            numbers.forEach { (k, v) ->
                line = line.replace(Regex(v), k)
            }
            var first = ""
            var firstPos = Int.MAX_VALUE
            var last = ""
            var lastPos = -1
            numbers.forEach { (k, v) ->
                val pos = line.indexOf(k)
                if (pos != -1 && pos < firstPos) {
                    first = k
                    firstPos = pos
                    return@forEach
                }
            }
            numbers.forEach { (k, v) ->
                val pos = line.lastIndexOf(k)
                if (pos != -1 && pos > lastPos) {
                    last = k
                    lastPos = pos
                    return@forEach
                }
            }

            val firstVal = numbers[first]!!.toInt()
            val lastVal = numbers[last]!!.toInt()

            calibration += ( firstVal * 10 + lastVal )
        }
        println("Calibration is: $calibration")
    }
}