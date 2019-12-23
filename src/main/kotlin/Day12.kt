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

    fun applyGravityFor(otherMoon: Moon) = with(position) {
        copy(
            velocity = Velocity(
                velocity.x + gravityRelativeTo(otherMoon) { x },
                velocity.y + gravityRelativeTo(otherMoon) { y },
                velocity.z + gravityRelativeTo(otherMoon) { z }
            )
        )
    }

    private fun Position.gravityRelativeTo(
        other: Moon,
        axis: Position.() -> Int
    ) = when {
        other.position.axis() > this.axis() -> 1
        other.position.axis() < this.axis() -> -1
        else -> 0
    }

}

fun List<Moon>.applyVelocity(): List<Moon> = map { it.applyVelocity() }

fun List<Moon>.applyGravity(): List<Moon> {
    return map { moon ->
        fold(moon) { current, other ->
            if (current != other) {
                current.applyGravityFor(other)
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
