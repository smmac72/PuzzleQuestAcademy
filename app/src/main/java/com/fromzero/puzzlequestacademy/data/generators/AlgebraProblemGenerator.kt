// data/generators/AlgebraProblemGenerator.kt

package com.fromzero.puzzlequestacademy.data.generators

import com.fromzero.puzzlequestacademy.data.model.AlgebraProblem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class AlgebraProblemGenerator {

    /**
     * Generates a random algebra problem.
     * @return An AlgebraProblem object.
     */
    suspend fun generateProblem(): AlgebraProblem = withContext(Dispatchers.Default) {
        val a = Random.nextInt(1, 10)
        val b = Random.nextInt(1, 10)
        val c = Random.nextInt(1, 10)
        val equation = "$a*x + $b = $c"
        val solution = (c - b) / a.toDouble()
        AlgebraProblem(equation = equation, solution = solution)
    }
}
