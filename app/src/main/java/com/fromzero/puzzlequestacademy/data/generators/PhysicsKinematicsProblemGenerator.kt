// data/generators/PhysicsKinematicsProblemGenerator.kt

package com.fromzero.puzzlequestacademy.data.generators

import com.fromzero.puzzlequestacademy.data.model.PhysicsProblem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class PhysicsKinematicsProblemGenerator {

    private val problems = listOf(
        PhysicsProblem(
            question = "A car accelerates from rest to 20 m/s in 5 seconds. What is its acceleration?",
            solution = 4.0
        ),
        PhysicsProblem(
            question = "A projectile is launched with an initial velocity of 15 m/s at an angle of 30 degrees. What is its horizontal velocity component?",
            solution = 12.99 // Approximately 13 m/s
        ),
        PhysicsProblem(
            question = "An object moves with a constant velocity of 10 m/s for 3 seconds. What is the distance covered?",
            solution = 30.0
        )
    )

    /**
     * Generates a Physics Kinematics Problem.
     * @return A PhysicsProblem object.
     */
    suspend fun generateProblem(): PhysicsProblem? = withContext(Dispatchers.Default) {
        problems.randomOrNull()
    }
}
