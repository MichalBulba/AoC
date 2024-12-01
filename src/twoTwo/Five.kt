package twoTwo

import java.io.File
import java.util.*

object Five {

    private fun getCrates() =  mutableListOf(
            stackOfReversed(),
            stackOfReversed('V', 'N', 'F', 'S', 'M', 'P', 'H', 'J'),
            stackOfReversed('Q', 'D', 'J', 'M', 'L', 'R', 'S'),
            stackOfReversed('B', 'W', 'S', 'C', 'H', 'D', 'Q', 'N'),
            stackOfReversed('L', 'C', 'S', 'R'),
            stackOfReversed('B', 'F', 'P', 'T', 'V', 'M'),
            stackOfReversed('C', 'N', 'Q', 'R', 'T'),
            stackOfReversed('R', 'V', 'G'),
            stackOfReversed('R', 'L', 'D', 'P', 'S', 'Z', 'C'),
            stackOfReversed('F', 'B', 'P', 'G', 'V', 'J', 'S', 'D')
    )

    fun resolve() {
        resolve1()
        resolve2()
    }

    private fun resolve1() {
        val crates = getCrates()
        val regex = "(\\d*) from (\\d*) to (\\d*)".toRegex()

        var index = 1
        File("22/input_five.txt").forEachLine {
            if (index >= 11) {
                val result = regex.find(it)
                val (hm, f, t) = result!!.destructured
                val howMany = hm.toInt()
                val from = f.toInt()
                val to = t.toInt()

                repeat(howMany) {
                    val crate = crates[from].pop()
                    crates[to].push(crate)
                }
            }
            index++
        }
        val result = StringBuilder(9)
        crates.forEach {
            if (it.isNotEmpty()) {
                result.append(it.pop())
            }
        }
        println("Top crates are $result")
    }

    private fun resolve2() {
        val crates = getCrates()
        val regex = "(\\d*) from (\\d*) to (\\d*)".toRegex()

        var index = 1
        File("22/input_five.txt").forEachLine {
            if (index >= 11) {
                val result = regex.find(it)
                val (hm, f, t) = result!!.destructured
                val howMany = hm.toInt()
                val from = f.toInt()
                val to = t.toInt()

                val sideStack = Stack<Char>()
                repeat(howMany) {
                    val crate = crates[from].pop()
                    sideStack.push(crate)
                }

                repeat(howMany) {
                    val crate = sideStack.pop()
                    crates[to].push(crate)
                }
            }
            index++
        }
        val result = StringBuilder(9)
        crates.forEach {
            if (it.isNotEmpty()) {
                result.append(it.pop())
            }
        }
        println("Top crates for 9001 are $result")
    }

    private fun <T> stackOf(vararg elements: T) = Stack<T>().apply {
        elements.forEach { push(it) }
    }

    private inline fun <reified T> stackOfReversed(vararg elements: T) =  stackOf(*elements.reversedArray())
}