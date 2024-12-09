package twoFour

import java.io.File
import java.time.Instant

object Nine {

    private var disc: String = ""
    private var files = mutableListOf<File>()
    private var spaces = mutableListOf<Int>()

    fun resolve() {
        resolve1()
        resolve2()
    }

    private fun resolve1() {
        val startT = Instant.now().toEpochMilli()
        File("24/input_nine.txt").forEachLine { line ->
            disc = line
        }

        disc.forEachIndexed { index, c ->
            val ci = c.toString().toInt()
            if (index % 2 == 0) {
                files.add(File(ci, index/2))
            } else {
                spaces.add(ci)
            }
        }

        val filess = ArrayDeque(files)
        val spacess = ArrayDeque(spaces)


        val compacted = buildList {
            while (filess.isNotEmpty() || spacess.isEmpty()) {
                //full file
                var file = filess.removeFirst()
                repeat(file.size) {
                    add(file.id)
                }
                var space = spacess.removeFirst()

                //fill spaces
                while (space > 0 && spacess.isNotEmpty() && filess.isNotEmpty()) {
                    file = filess.removeLast()
                    if(space >= file.size) {
                        repeat(file.size) {
                            add(file.id)
                        }
                        space -= file.size
                    } else {
                        repeat(space) {
                            add(file.id)
                        }
                        filess.add(file.copy(size = file.size - space))
                        space = 0
                    }
                }
            }
        }
//        println(compacted)

        val checksum: Long = compacted.mapIndexed { index, c -> index * c.toLong()}.sum()
        val endT = Instant.now().toEpochMilli()

        println("Checksum is $checksum")
        println("Run for: ${endT - startT}ms")
    }

    private data class File(val size: Int, val id: Int) {
        fun isFile() = id > -1

        override fun toString(): String {
            return buildString {
                repeat(size) {
                    append(if(id == -1) "." else id)
                }
            }
        }
    }

    private fun resolve2() {
        println("Part2")
        val startT = Instant.now().toEpochMilli()

        val system = ArrayDeque<File>(files.size + spaces.size)

        files.forEachIndexed { i, it ->
            system.add(it)
            if (i < spaces.size) {
                system.add(File(spaces[i], -1))
            }
        }

        val touched = mutableSetOf<File>()
            var r = system.lastOrNull { it.isFile() && !touched.contains(it) }
            while (r != null) {
                touched.add(r)
                val l = system.firstOrNull { !it.isFile() && it.size >= r!!.size }
                if(l != null && (system.indexOf(l) < system.indexOf(r))) {
                    val ri = system.indexOf(r)
                    system.removeAt(ri)
                    system.add(ri, File(size = r.size, id = -1))

                    val li = system.indexOf(l)
                    system.removeAt(li)
                    system.add(li, r)
                    system.add(li+1, File(size = l.size - r.size, id = -1))
                }
                r = system.lastOrNull { it.isFile() && !touched.contains(it) }
            }

        val compacted = buildList<Int> {
            system.forEach { f: File ->
                repeat(f.size) {
                    if(f.isFile()) {
                        add(f.id)
                    } else {
                        add(0)
                    }
                }
            }
        }


        val comp = compacted.joinToString("") { it.toString() }
        val checksum: Long = compacted.mapIndexed { index, c -> index * c.toLong()}.sum()
        val endT = Instant.now().toEpochMilli()

        println("Checksum is $checksum")
        println("Run for: ${endT - startT}ms")
    }
}
