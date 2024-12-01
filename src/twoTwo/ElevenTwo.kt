package twoTwo

import java.io.File

object ElevenTwo {

    private const val MONKEYS = 7

    fun resolve() {
//        resolve1()
        resolve2()
    }

    private fun resolve2() {
        val monkeys = mutableListOf<Monkey>()
        val inspections = mutableListOf(0L,0L,0L,0L,0L,0L,0L,0L)

        val br = File("22/input_eleven.txt").bufferedReader()

        (0..MONKEYS).forEach { id ->
            br.readLine()
            val items = br.readLine().substring(18).split(", ").map { it.toLong() }
            val operations = br.readLine().substring(23).split(" ")
            val testNumber = br.readLine().substring(21).toInt()
            val trueNum = br.readLine().substring(29).toInt()
            val falseNum = br.readLine().substring(30).toInt()

            val operationNumber = if (operations[1] == "old") -1 else operations[1].toLong()
            val operation = Operation.forOperator(operations[0], operationNumber)
            val test = Test(testNumber, trueNum, falseNum)

            val monkey = Monkey(id, items.toMutableList(), operation, test)
            monkeys.add(monkey)
            br.readLine()
        }
        var t = 1
        repeat(10000) {
//            println("Iteration: $t")
            val thrown = mutableListOf<Pair<Int, Long>>()
            monkeys.iterator().forEach {
                it.examine(thrown, inspections)
            }
            thrown.forEach {
                monkeys[it.first].items.add(it.second)
            }
            thrown.clear()
            t++
        }

        val monkeyLevel = inspections.sorted().takeLast(2).reduce { acc, i -> acc * i }
        println("Monkey level business is: $monkeyLevel")
    }

    data class Monkey(val id: Int, val items: MutableList<Long>, val operation: Operation, val test: Test) {
        fun examine(thrown: MutableList<Pair<Int, Long>>, inspections: MutableList<Long>) {
            items.addAll(thrown.filter { it.first == id }.map { it.second })
            thrown.removeAll {  it.first == id }
            items.forEach { item ->
                var worryLevel = operation.applyTo(item)
//                println(worryLevel)
                var nextMonkey = test.doTest(worryLevel)
//                val (worryLevel, nextMonkey) = optimisation(item, operation, test)
                var toThrow = if (worryLevel % test.number == 0L) worryLevel else if (operation is Multiply) item * (worryLevel % test.number) else item + (worryLevel % test.number)
                thrown.add(nextMonkey to toThrow)
                inspections[id]++
            }
            items.clear()
        }
    }

//    fun optimisation(item: Long, operation: Operation, test: Test): Pair<Long, Int> {
//
//    }
    data class Test(val number: Int, val ifTrue: Int, val ifFalse : Int) {
        fun doTest(worryLevel: Long) = if(worryLevel % number == 0L) {
            ifTrue
        } else {
            ifFalse
        }
    }

    interface Operation {

        companion object {
            fun forOperator(operator: String, number: Long) = when(operator) {
                "*" -> Multiply(number)
                "+" -> Plus(number)
                else -> throw IllegalArgumentException("Unknown operator $operator")
            }
        }
        fun applyTo(src: Long) : Long

    }

    class Multiply(private val number: Long): Operation {
        override fun applyTo(src: Long) = if (number > 0 ) src * number else src * src
    }

    class Plus(private val number: Long): Operation {
        override fun applyTo(src: Long) = src + number

    }
}