// data/generators/GeometryProblemGenerator.kt

package com.fromzero.puzzlequestacademy.data.generators

import com.fromzero.puzzlequestacademy.data.model.GeometryProblem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class GeometryProblemGenerator {

    private val questionsAndSolutions = listOf(
        GeometryProblem(
            question = "What is the area of a triangle with base 5 cm and height 10 cm?",
            solution = 25.0
        ),
        GeometryProblem(
            question = "What is the circumference of a circle with radius 7 cm? (Use π = 3.14)",
            solution = 43.96
        ),
        GeometryProblem(
            question = "What is the volume of a cube with side length 4 cm?",
            solution = 64.0
        )
    )

    /**
     * Generates a Geometry Problem.
     * @return A GeometryProblem object.
     */
    suspend fun generateProblem(): GeometryProblem? = withContext(Dispatchers.Default) {
        questionsAndSolutions.randomOrNull()
    }
}
