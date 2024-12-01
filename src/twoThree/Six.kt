package twoThree

object Six {

//    private val lines = File("23/input_six.txt").readLines()
//    private val lineLength = lines[0].length

    private val inputs = listOf(
            Input(40, 277),
            Input(82, 1338),
            Input(91, 1349),
            Input(66, 1063)
    )

    private val input2 = Input(40829166, 277133813491063)


    fun resolve() {
        resolve1()
        resolve2()
    }

    private fun resolve1() {
        val optionsList = inputs.map { it.options() }
        val options = optionsList.fold(1) { acc, t -> acc*t }

        println("Min location: $options")

    }

    private fun resolve2() {
        val options = input2.options()

        println("Min location2: $options")
    }

    data class Input(val time: Long, val distance: Long) {

        fun options(): Int {
            var options = 0
            for(t in 0..time) {
                if (t * (time - t) > distance ){
                    options++
                }
            }
            return options
        }
    }


}