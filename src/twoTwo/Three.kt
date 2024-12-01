package twoTwo

import java.io.File

object Three {
    private const val ASCI_A = 65
    private const val ASCI_a = 97

    private const val A_PRIO = 27
    private const val a_PRIO = 1
    fun resolve() {
        resolve1()
        resolve2()
    }

    private fun resolve1() {
        var errorsPriority = 0
        val errors = mutableSetOf<Int>()
        File("22/input_three.txt").forEachLine {
            val first = it.substring(0, it.length / 2)
            val second = it.substring(it.length / 2, it.length)

            first.forEach { firstC ->
                second.forEach { secondC ->
                    if (firstC == secondC) {
                        errors.add(firstC.priority())
                    }
                }
            }
            errorsPriority += errors.sum()
            errors.clear()
        }
        println("Rucksacks priority is  $errorsPriority")
    }

    private fun Char.priority() = when {
        code >= ASCI_a -> code - ASCI_a + a_PRIO
        else -> code - ASCI_A + A_PRIO
    }

    private fun resolve2() {
        var index = 1
        var first = ""
        var second = ""
        var error = 0
        val errors = mutableSetOf<Int>()
        File("22/input_three.txt").forEachLine { line ->
            if (index == 1) first = line
            if (index == 2) second = line
            if (index == 3) {
                line.forEach { char ->
                    if (first.contains(char) && second.contains(char)) {
                        errors.add(char.priority())
                    }
                }
                error += errors.sum()
                errors.clear()
                index = 1
            } else {
                index++
            }
        }
        println("Rucksacks with badges $error")
    }
}