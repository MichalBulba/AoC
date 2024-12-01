package twoTwo

import java.io.File

object Eleven {

    private const val MONKEYS = 7

    fun resolve() {
        resolve1()
//        resolve2()
    }

    private fun resolve1() {
        val monkeys = mutableListOf<Monkey>()
        val inspections = mutableListOf(0,0,0,0,0,0,0,0)

        val br = File("22/input_eleven.txt").bufferedReader()

        (0..MONKEYS).forEach { id ->
            br.readLine()
            val items = br.readLine().substring(18).split(", ").map { it.toInt() }
            val operations = br.readLine().substring(23).split(" ")
            val testNumber = br.readLine().substring(21).toInt()
            val trueNum = br.readLine().substring(29).toInt()
            val falseNum = br.readLine().substring(30).toInt()

            val operationNumber = if (operations[1] == "old") -1 else operations[1].toInt()
            val operation = Operation.forOperator(operations[0], operationNumber)
            val test = Test(testNumber, trueNum, falseNum)

            val monkey = Monkey(id, items.toMutableList(), operation, test)
            monkeys.add(monkey)
            br.readLine()
        }
        repeat(20) {
            val thrown = mutableListOf<Pair<Int, Int>>()
            monkeys.iterator().forEach {
                it.examine(thrown, inspections)
            }
            thrown.forEach {
                monkeys[it.first].items.add(it.second)
            }
            thrown.clear()
        }

        val monkeyLevel = inspections.sorted().takeLast(2).reduce { acc, i -> acc * i }
        println("Monkey level business is: $monkeyLevel")
    }

    private fun resolve2() {

        File("22/input_eleven.txt").forEachLine { line ->

        }

//        println("Best scenic score is: $best")
    }

    data class Monkey(val id: Int, val items: MutableList<Int>, val operation: Operation, val test: Test) {
        fun examine(thrown: MutableList<Pair<Int, Int>>, inspections: MutableList<Int>) {
            items.addAll(thrown.filter { it.first == id }.map { it.second })
            thrown.removeAll {  it.first == id }
            items.forEach { item ->
                val worryLevel = operation.applyTo(item).toFloat()
//                val worryLevelDecreased = (worryLevel / 3f).roundToInt() // bad description
                val worryLevelDecreased = (worryLevel / 3).toInt()
                val nextMonkey = test.doTest(worryLevelDecreased)
                thrown.add(nextMonkey to worryLevelDecreased)
                inspections[id]++
            }
            items.clear()
        }
    }
    data class Test(val number: Int, val ifTrue: Int, val ifFalse : Int) {
        fun doTest(worryLevel: Int) = if(worryLevel % number == 0) {
            ifTrue
        } else {
            ifFalse
        }
    }

    interface Operation {

        companion object {
            fun forOperator(operator: String, number: Int) = when(operator) {
                "*" -> Multiply(number)
                "+" -> Plus(number)
                else -> throw IllegalArgumentException("Unknown operator $operator")
            }
        }
        fun applyTo(src: Int) : Int

    }

    class Multiply(private val number: Int): Operation {
        override fun applyTo(src: Int) = if (number > 0 ) src * number else src * src
    }

    class Plus(private val number: Int): Operation {
        override fun applyTo(src: Int) = src + number

    }
}