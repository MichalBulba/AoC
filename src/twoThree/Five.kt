package twoThree

import java.io.File
import java.time.Instant

object Five {

    private val lines = File("23/input_five.txt").readLines()
    private val lineLength = lines[0].length
    fun resolve() {
        resolve1()
//        resolve2()
    }

    private fun resolve1() {
        val start = Instant.now()
        var i = 0
        //seeds
        val seeds = lines[i].replace("seeds: ", "").split(" ").map { it.toLong() }
        i+=3
        //seed to soil
        val seedToSoil = mutableListOf<To>()
        while ( lines[i].isNotEmpty()) {
            val (destination, start, range) = lines[i].split(" ").map { it.toLong() }
            seedToSoil.add(To(LongRange(start, start+range-1), LongRange(destination, destination+range-1), range))
            i++
        }
        i+=2
        //soil to fertilizer
        val soilToFertilizer = mutableListOf<To>()
        while ( lines[i].isNotEmpty()) {
            val (destination, start, range) = lines[i].split(" ").map { it.toLong() }
            soilToFertilizer.add(To(LongRange(start, start+range-1), LongRange(destination, destination+range-1), range))
            i++
        }
        i+=2
        //fertilizer to water
        val fertilizerToWater = mutableListOf<To>()
        while ( lines[i].isNotEmpty()) {
            val (destination, start, range) = lines[i].split(" ").map { it.toLong() }
            fertilizerToWater.add(To(LongRange(start, start+range-1), LongRange(destination, destination+range-1), range))
            i++
        }
        i+=2
        //water to light
        val waterToLight = mutableListOf<To>()
        while ( lines[i].isNotEmpty()) {
            val (destination, start, range) = lines[i].split(" ").map { it.toLong() }
            waterToLight.add(To(LongRange(start, start+range-1), LongRange(destination, destination+range-1), range))
            i++
        }
        i+=2
        //light to temperature
        val lightToTemperature = mutableListOf<To>()
        while ( lines[i].isNotEmpty()) {
            val (destination, start, range) = lines[i].split(" ").map { it.toLong() }
            lightToTemperature.add(To(LongRange(start, start+range-1), LongRange(destination, destination+range-1), range))
            i++
        }
        i+=2
        //temperature to humidity
        val temperatureToHumidity = mutableListOf<To>()
        while ( lines[i].isNotEmpty()) {
            val (destination, start, range) = lines[i].split(" ").map { it.toLong() }
            temperatureToHumidity.add(To(LongRange(start, start+range-1), LongRange(destination, destination+range-1), range))
            i++
        }
        i+=2
        //temperature to humidity
        val humidityToLocation = mutableListOf<To>()
        while ( lines[i] != "q") {
            val (destination, start, range) = lines[i].split(" ").map { it.toLong() }
            humidityToLocation.add(To(LongRange(start, start+range-1), LongRange(destination, destination+range-1), range))
            i++
        }

        //part1
//        val minLocation = seeds.minOf { seed ->
//            val soil = navigateOne(seedToSoil, seed)
//            val fertilizer = navigateOne(soilToFertilizer, soil)
//            val water = navigateOne(fertilizerToWater, fertilizer)
//            val light = navigateOne(waterToLight, water)
//            val temperature = navigateOne(lightToTemperature, light)
//            val humidity = navigateOne(temperatureToHumidity, temperature)
//            val location = navigateOne(humidityToLocation, humidity)
//            location
//        }
//        println("Min location: $minLocation")

        val seedsRange = mutableListOf<LongRange>()
        for (s in (0 until seeds.size / 2)) {
            seedsRange.add(LongRange(seeds[2*s], seeds[2*s] + seeds[2*s+1] -1))
        }

        //brute this works fine!
        var min2 = Long.MAX_VALUE

        for (seed in seedsRange) {
            println("Range $seed")
            val min = seed.minOf { seed ->
                val soil = navigateOne(seedToSoil, seed)
                val fertilizer = navigateOne(soilToFertilizer, soil)
                val water = navigateOne(fertilizerToWater, fertilizer)
                val light = navigateOne(waterToLight, water)
                val temperature = navigateOne(lightToTemperature, light)
                val humidity = navigateOne(temperatureToHumidity, temperature)
                val location = navigateOne(humidityToLocation, humidity)
                location
            }
            if (min < min2) min2 = min
        }

        println("Min location: $min2")

//        val soils = from.flatMap { it.split(fromToDest)}
        println("soils")
        val soils = seedsRange.flatMap { it.split(seedToSoil) }.combine()
        println("fertilizers from ${soils.size}")
        val fertilizers = soils.flatMap { it.split(soilToFertilizer) }.combine()
        println("waters from ${fertilizers.size}")
        val waters = fertilizers.flatMap { it.split(fertilizerToWater) }.combine()
        println("lights from ${waters.size}")
        val lights = waters.flatMap { it.split(waterToLight) }.combine()
        println("temperatures from ${lights.size}")
        val temperatures = lights.flatMap { it.split(lightToTemperature) }.combine()
        println("humidities from ${temperatures.size}")
        val humidities = temperatures.flatMap { it.split(temperatureToHumidity) }.combine()
        println("locations from ${humidities.size}")
//        println(humidities)

        val locations = humidities.flatMapIndexed {
            i, it ->
            if (i % 1000000 == 0) println(i/1000000)
            it.split(humidityToLocation)
        }

        val min = locations.sortedBy { it.first }
        println("Min location: ${min.first().first}")
        println("Duration ${Instant.now().toEpochMilli() -start.toEpochMilli()}")

    }

    private fun navigateOne(fromToDest: List<To>, from: Long): Long {
        val destTo: To? = fromToDest.firstOrNull { it.start.contains(from) }
        val dest = from + (destTo?.let { destTo.destination.first - destTo.start.first } ?: 0)
        return dest
    }

//    private fun navigateRange(fromToDest: List<To>, from: List<LongRange>): List<LongRange> {
//        val soils = from.flatMap { it.split(fromToDest)}
//        val fertilizers = soils.flatMap { it.split() }
//    }

    private fun resolve2() {
        val a = LongRange(1, 15)
        println(a.split(listOf(To(LongRange(1,4), LongRange(11,14), 2), To(LongRange(10,12), LongRange(20,22), 2))))


    }

    data class To(val start: LongRange, val destination: LongRange, val range: Long)

    fun LongRange.split(fromToDest: List<To>): List<LongRange> {
        val sortedDest = fromToDest.sortedBy { it.start.first }
        val ranges = mutableListOf<LongRange>()

        var start = first
        for (to in sortedDest) {
            if(start < to.start.first) {
                ranges.add(LongRange(start, to.start.first-1))
                start = ranges.last().last.plus(1)
            }

            val space = to.destination.first - to.start.first
            if (start < to.start.last) {
                if (start >= to.start.first) {
                    if (last <= to.start.last) {
                        ranges.add(LongRange(start + space, last + space))
                        start = last
                        break
                    } else {
                        ranges.add(LongRange(start + space, to.start.last + space))
                        start = to.start.last + 1
                    }
                } else {
                    if (last < to.start.first) {
                        ranges.add(LongRange(start, last))
                        break
                    } else {
                        ranges.add(LongRange(start, to.start.first - 1))
                        start = to.start.first
                    }
                }
            }
            val j = 1
        }
        if(start < last) {
            ranges.add(LongRange(start, last))
        }

        return ranges
    }

    private fun List<LongRange>.combine(): List<LongRange> {
        val set = toSet()
        val sortedDest = set.sortedBy { it.first }
        val ranges = mutableListOf<LongRange>()

//        sortedDest.forEachIndexed { i, it ->
//            if (i < ranges.size - 1) {
//                ranges.
//            }
//        }
        return sortedDest
    }
}
