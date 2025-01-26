// data/generators/PatternRecognitionGenerator.kt

package com.fromzero.puzzlequestacademy.data.generators

import com.fromzero.puzzlequestacademy.data.model.PatternProblem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class PatternRecognitionGenerator {

    private val patterns = listOf(
        PatternProblem(
            sequence = listOf(2, 4, 6, 8, 10),
            nextElement = 12
        ),
        PatternProblem(
            sequence = listOf(1, 1, 2, 3, 5, 8),
            nextElement = 13
        ),
        PatternProblem(
            sequence = listOf(5, 10, 20, 40),
            nextElement = 80
        )
    )

    /**
     * Generates a Pattern Recognition Problem.
     * @return A PatternProblem object.
     */
    suspend fun generateProblem(): PatternProblem? = withContext(Dispatchers.Default) {
        patterns.randomOrNull()
    }
}
