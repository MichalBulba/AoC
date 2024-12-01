package twoTwo

import java.io.File
import java.util.*

object Six {

    private const val START_PACKET_LENGTH = 4
    private const val MSG_PACKET_LENGTH = 14

    fun resolve() {
        resolve1()
        resolve2()
    }

    private fun resolve1() {
        var startIndex = 0
        val buffer = LinkedList<Char>()
        File("22/input_six.txt").forEachLine { line ->
            line.forEachIndexed { index, char ->
                buffer.add(char)
                if (buffer.size == START_PACKET_LENGTH) {
                    if (buffer.distinct().size == START_PACKET_LENGTH) {
                        startIndex = index + 1
                        return@forEachIndexed
                    } else {
                        buffer.removeAt(0)
                    }
                }
            }
        }

        println("First start of after $startIndex")
    }

    private fun resolve2() {
        var startIndex = 0
        val buffer = LinkedList<Char>()
        File("22/input_six.txt").forEachLine { line ->
            line.forEachIndexed { index, char ->
                buffer.add(char)
                if (buffer.size == MSG_PACKET_LENGTH) {
                    if (buffer.distinct().size == MSG_PACKET_LENGTH) {
                        startIndex = index + 1
                        return@forEachIndexed
                    } else {
                        buffer.removeAt(0)
                    }
                }
            }
        }

        println("First msg after $startIndex")
    }

}