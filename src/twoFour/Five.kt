package twoFour

import java.io.File

object Five {
    fun resolve() {
        resolve1()
        resolve2()
    }

    val rules = hashMapOf<String, MutableList<String>>()

    private fun resolve1() {
        var sum = 0
        File("24/input_five.txt").forEachLine { line ->
            if (line.contains('|')) {
                val (a, b) = line.split("|")
                rules[a]?.add(b) ?: mutableListOf<String>().let {
                    it.add(b)
                    rules.put(a, it)
                }
            }

            if (line.contains(',')) {
                val numbers = line.split(",")
                if (numbers.isValid()) sum += numbers[numbers.size/2].toInt()
            }
        }
        println("Sum is $sum")
    }

    private fun List<String>.isValid(): Boolean {
        this.forEachIndexed { i, it ->
            if(i > 0) {
                val check = this.subList(0, i)
                if(rules[it]?.any { r -> check.contains(r) } == true) {
                    return false
                }
            }
        }
        return true
    }

    private fun resolve2() {
        var sum = 0
        File("24/input_five.txt").forEachLine { line ->
            if (line.contains('|')) {
                val (a, b) = line.split("|")
                rules[a]?.add(b) ?: mutableListOf<String>().let {
                    it.add(b)
                    rules.put(a, it)
                }
            }

            if (line.contains(',')) {
                val numbers = line.split(",")
                if (!numbers.isValid()) {
                    val fixed = numbers.sortedWith { l, r -> if(rules[l]?.contains(r) == true) -1 else 1 }
                    println(fixed)
                    //corrected
                    sum += fixed[fixed.size/2].toInt()
                }
            }
        }
        println("Corrected sum is $sum")

        println("")
    }
}