fun List<String>.toOrbits(): List<Pair<String, String>> {
    return map { line ->
        val (planet, satellite) = line.split(")")
        satellite to planet
    }
}

fun List<Pair<String, String>>.countAll(): Int {

    val mapOfOrbits = this.toMap()

    fun String.countOrbits(count: Int = 0): Int {
        val planet = mapOfOrbits[this]
        return planet?.countOrbits(count + 1) ?: count
    }

    val unzipped = unzip()
    val allPlanets = (unzipped.first + unzipped.second).toSet()

    return allPlanets
        .sumBy { it.countOrbits() }
}

