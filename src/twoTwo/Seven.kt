package twoTwo

import java.io.File

object Seven {

    val lines = File("22/input_seven.txt").readLines()

    fun resolve() {
        resolve1()
//        resolve2()
    }

    private fun resolve1() {
        val filesystem = Dir("/", mutableListOf())


        println("First start of after ")
    }

    private fun resolve2() {

        println("First msg after ")
    }

    fun String.isCD() = contains("cd")
    fun String.parseCD() = "\\$ cd (.*)".toRegex().find(this)?.destructured

    data class Dir(val name: String, val files: MutableList<File>)
    data class File(val name: String, val size: Int)
}