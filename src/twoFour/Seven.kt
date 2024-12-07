package twoFour

import java.io.File
import java.lang.Exception
import java.time.Instant
import java.util.concurrent.atomic.AtomicLong

object Seven {


    fun resolve() {
//        resolve1()
        resolve2()
    }

    private fun resolve1() {
        val startT = Instant.now().toEpochMilli()

        val sum = AtomicLong(0)
        File("24/input_seven.txt").forEachLine { line ->
            val (result_s, number_s) = line.split(":").map { it.trim() }
            val result = result_s.toLong()
            val numbers = number_s.split(" ").map { it.toLong() }

            var operatorsA = MutableList(numbers.size - 1) { Operator.ADD }
            var operatorsM = MutableList(numbers.size - 1) { Operator.MULTIPLY }
            var operatorsG = MutableList(numbers.size - 1) { Operator.GLUE }

            //iterate over all possible combinations od operatorsA and operatorsM
            for (i in 0 until Math.pow(2.0, numbers.size - 1.0).toLong()) {
                val operators = i.toString(2).padStart(numbers.size - 1, '0').map { if (it == '0') Operator.ADD else Operator.MULTIPLY }
//                println(operators)
                val resultA = numbers.apply(operators)
                if (resultA == result) {
                    sum.addAndGet(result)
                    println(result)
                    break
                }
            }
            println()
        }
        println("Sum: ${sum.get()}")

        val endT = Instant.now().toEpochMilli()
        println("Run for: ${endT-startT}ms")
    }

    private fun List<Long>.apply(operators: List<Operator>) = this.reduceIndexed  { index, acc, it ->
        if (index == 0 ) it
        else operators[index-1].apply(acc, it)
    }

    private enum class Operator {
        ADD {
            override fun apply(a: Long, b: Long) = a + b
        },
        MULTIPLY {
            override fun apply(a: Long, b: Long) = a * b
        },
        GLUE {
            override fun apply(a: Long, b: Long) = (a.toString() +  b.toString()).toLong()
        };
        abstract fun apply(a: Long, b: Long): Long
    }

    private fun resolve2() {
        val startT = Instant.now().toEpochMilli()

        val sum = AtomicLong(0)
        File("24/input_seven.txt").forEachLine { line ->
            val (result_s, number_s) = line.split(":").map { it.trim() }
            val result = result_s.toLong()
            val numbers = number_s.split(" ").map { it.toLong() }

            var operatorsA = MutableList(numbers.size - 1) { Operator.ADD }
            var operatorsM = MutableList(numbers.size - 1) { Operator.MULTIPLY }
            var operatorsG = MutableList(numbers.size - 1) { Operator.GLUE }

            //iterate over all possible combinations of operatorsA, operatorsM and operatorsG
            for (i in 0 until Math.pow(3.0, numbers.size - 1.0).toLong()) {
                val operators = i.toString(3).padStart(numbers.size - 1, '0').map {
                    when (it) {
                        '0' -> Operator.ADD
                        '1' -> Operator.MULTIPLY
                        '2' -> Operator.GLUE
                        else -> throw Exception("Invalid operator")
                    }
                }
//                println(operators)
                val resultA = numbers.apply(operators)
                if (resultA == result) {
                    sum.addAndGet(result)
                    println(result)
                    break
                }
            }
            println()
        }
        println("Sum: ${sum.get()}")

        val endT = Instant.now().toEpochMilli()
        println("Run for: ${endT-startT}ms")
    }
}