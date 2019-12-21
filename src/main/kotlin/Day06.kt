fun List<String>.toOrbits(): List<Pair<String, String>> {
    return map { line ->
        val (planet, satellite) = line.split(")")
        satellite to planet
    }
}

fun List<Pair<String, String>>.countAll() = getAllOrbits().values.sumBy { it.size }

fun List<Pair<String, String>>.getAllOrbits(): Map<String, List<String>> {

    val mapOfOrbits = this.toMap()

    fun String.getOrbits(orbits: List<String> = listOf()): List<String> {
        val planet = mapOfOrbits[this]
        return planet?.getOrbits(orbits + planet) ?: orbits
    }

    val unzipped = unzip()
    val allPlanets = (unzipped.first + unzipped.second).toSet()

    return allPlanets
        .map { it to it.getOrbits() }
        .toMap()
}

fun List<String>.minimalOrbitalTransfers(otherPath: List<String>): Int {
    return filter { it in otherPath }
        .map { indexOf(it) + otherPath.indexOf(it) }
        .min()
        ?: error("Something went wrong")
}

fun List<Pair<String, String>>.minOrbitalTransfersBetweenYOUAndSAN(): Int {
    val allOrbits = getAllOrbits()

    val sanOrbits = allOrbits["SAN"] ?: error("SAN should be in there...")
    val youOrbits = allOrbits["YOU"]?: error("YOU should be in there...")

    return sanOrbits.minimalOrbitalTransfers(youOrbits)
}

