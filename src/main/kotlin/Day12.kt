import kotlin.math.absoluteValue

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

data class AstralSystem(val moons: List<Moon>) {

    val energy: Int
        get() = moons.sumBy { it.potentialEnergy * it.kineticEnergy }

    companion object {
        private val pattern = Regex("pos=<x=([^,]+), y=([^,]+), z=([^>]+)>, vel=<x=([^,]+), y=([^,]+), z=([^>]+)>")
        private val alternativePattern = Regex("<x=([^,]+), y=([^,]+), z=([^>]+)>")

        fun of(input: String): AstralSystem {
            return AstralSystem(
                input
                    .split("\n")
                    .mapNotNull {
                        val matchResult = pattern.find(it)

                        if(matchResult != null) {
                            val (posX, posY, posZ, velX, velY, velZ) = matchResult.destructured
                            Moon(Position.of(posX, posY, posZ), Velocity.of(velX, velY, velZ))
                        } else {
                            val alternativeMatchResult = alternativePattern.find(it)
                            if(alternativeMatchResult != null) {
                                val (posX, posY, posZ) = alternativeMatchResult.destructured
                                Moon(Position.of(posX, posY, posZ), Velocity(0, 0, 0))
                            } else null
                        }
                    })
        }
    }

    fun applyVelocity() = AstralSystem(moons.map { it.applyVelocity() })

    fun applyGravity() = AstralSystem(moons.map { moon ->
        moons.fold(moon) { current, other ->
            if (current != other) {
                current.applyGravityFor(other)
            } else current
        }
    })

    fun step(times: Int): AstralSystem {
        return (1..times).fold(this) { acc, _ ->
            acc
                .applyGravity()
                .applyVelocity()
        }
    }

}

data class Moon(val position: Position, val velocity: Velocity) {

    val kineticEnergy: Int = with(position) {
        x.absoluteValue + y.absoluteValue + z.absoluteValue
    }

    val potentialEnergy: Int = with(velocity) {
        x.absoluteValue + y.absoluteValue + z.absoluteValue
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


