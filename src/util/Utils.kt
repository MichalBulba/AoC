package util

import kotlin.math.abs

object Utils {

    fun calculateNWW(a: Long, b: Long): Long {
        require(a != 0L || b != 0L) { "At least one of the numbers should be non-zero." }

        return abs(a * b) / calculateNWD(a, b)
    }

    fun calculateNWW(vararg numbers: Long): Long {
        require(numbers.isNotEmpty()) { "At least one number is required." }

        // Calculate LCM iteratively for multiple numbers
        var resultLCM = numbers[0]
        for (i in 1 until numbers.size) {
            resultLCM = calculateNWW(resultLCM, numbers[i])
        }

        return resultLCM
    }

    fun calculateNWD(x: Long, y: Long): Long {
        return if (y == 0L) abs(x) else calculateNWD(y, x % y)
    }
}