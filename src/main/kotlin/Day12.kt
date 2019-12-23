data class Position(val x: Int, val y: Int, val z: Int) {
    companion object {
        fun of(x: String, y: String, z: String) = Position(x.asInt(), y.asInt(), z.asInt())

    }
}

data class Velocity(val x: Int, val y: Int, val z: Int) {
    companion object {
        fun of(x: String, y: String, z: String) = Velocity(x.asInt(), y.asInt(), z.asInt())
    }
}

private fun String.asInt() = trim().toInt()

data class Moon(val position: Position, val velocity: Velocity) {

    companion object {
        private val pattern = Regex("pos=<x=([^,]+), y=([^,]+), z=([^>]+)>, vel=<x=([^,]+), y=([^,]+), z=([^>]+)>")

        fun of(input: String): List<Moon> {
            return input
                .split("\n")
                .mapNotNull { pattern.find(it) }
                .map {
                    val (posX, posY, posZ, velX, velY, velZ) = it.destructured
                    Moon(Position.of(posX, posY, posZ), Velocity.of(velX, velY, velZ))
                }
        }
    }

    fun applyVelocity() = with(position) {
        copy(
            position = Position(
                x + velocity.x,
                y + velocity.y,
                z + velocity.z
            )
        )
    }

    fun applyGravity(otherMoon: Moon) = with(position) {
        copy(
            velocity = Velocity(
                velocity.x + gravityRelativeTo(otherMoon) { it.x },
                velocity.y + gravityRelativeTo(otherMoon) { it.y },
                velocity.z + gravityRelativeTo(otherMoon) { it.z }
            )
        )
    }

    private fun Position.gravityRelativeTo(
        other: Moon,
        axisGetter: (Position) -> Int
    ) = when {
        axisGetter(other.position) > axisGetter(this) -> 1
        axisGetter(other.position) < axisGetter(this) -> -1
        else -> 0
    }

}

fun List<Moon>.applyVelocity(): List<Moon> = map { it.applyVelocity() }

fun List<Moon>.applyGravity(): List<Moon> {
    return map { planet ->
        fold(planet) { current, otherPlanet ->
            if (current != otherPlanet) {
                current.applyGravity(otherPlanet)
            } else current
        }
    }
}

fun List<Moon>.step(times: Int): List<Moon> {
    return (1..times).fold(this) { acc, _ ->
        acc
            .applyGravity()
            .applyVelocity()
    }
}
