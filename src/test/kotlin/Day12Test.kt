import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

class Day12Test {

    @Test
    fun `should apply velocity`() {
        val result = Moon(Position(1, 2, 3), Velocity(-2, 0, 3)).applyVelocity()
        result shouldBe Moon(Position(-1, 2, 6), Velocity(-2, 0, 3))

        listOf(
            Moon(Position(3, 0, 0), Velocity(1, 0, 0)),
            Moon(Position(5, 0, 0), Velocity(-1, 0, 0))
        ).applyVelocity() shouldBe listOf(
            Moon(Position(4, 0, 0), Velocity(1, 0, 0)),
            Moon(Position(4, 0, 0), Velocity(-1, 0, 0))
        )
    }

    @Test
    fun `should apply gravity`() {
        listOf(
            Moon(Position(3, 0, 0), Velocity(0, 0, 0)),
            Moon(Position(5, 0, 0), Velocity(0, 0, 0))
        ).applyGravity() shouldBe listOf(
            Moon(Position(3, 0, 0), Velocity(1, 0, 0)),
            Moon(Position(5, 0, 0), Velocity(-1, 0, 0))
        )
    }

    @Test
    fun `should parse moons`() {
        Moon.of(
            """
            pos=<x= 2, y=-1, z= 1>, vel=<x= 3, y=-1, z=-1>
            pos=<x= 3, y=-7, z=-4>, vel=<x= 1, y= 3, z= 3>
            pos=<x= 1, y=-7, z= 5>, vel=<x=-3, y= 1, z=-3>
            pos=<x= 2, y= 2, z= 0>, vel=<x=-1, y=-3, z= 1>
        """.trimIndent()
        ) shouldBe listOf(
            Moon(Position(2, -1, 1), Velocity(3, -1, -1)),
            Moon(Position(3, -7, -4), Velocity(1, 3, 3)),
            Moon(Position(1, -7, 5), Velocity(-3, 1, -3)),
            Moon(Position(2, 2, 0), Velocity(-1, -3, 1))
        )
    }

    @Test
    fun `should step correctly`() {
        val moons = Moon.of(
            """
            pos=<x=-1, y=  0, z= 2>, vel=<x= 0, y= 0, z= 0>
            pos=<x= 2, y=-10, z=-7>, vel=<x= 0, y= 0, z= 0>
            pos=<x= 4, y= -8, z= 8>, vel=<x= 0, y= 0, z= 0>
            pos=<x= 3, y=  5, z=-1>, vel=<x= 0, y= 0, z= 0>
        """.trimIndent()
        )

        moons.step(10) shouldBe listOf(
            Moon(Position(2, 1, -3), Velocity(-3, -2, 1)),
            Moon(Position(1, -8, 0), Velocity(-1, 1, 3)),
            Moon(Position(3, -6, 1), Velocity(3, 2, -3)),
            Moon(Position(2, 0, 4), Velocity(1, -1, -1))
        )
    }
}





