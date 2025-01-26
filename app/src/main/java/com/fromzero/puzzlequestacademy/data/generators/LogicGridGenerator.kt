// data/generators/LogicGridGenerator.kt

package com.fromzero.puzzlequestacademy.data.generators

import com.fromzero.puzzlequestacademy.data.model.LogicGridPuzzle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LogicGridGenerator {

    private val puzzles = listOf(
        LogicGridPuzzle(
            clues = listOf(
                "The person who owns the red house drinks coffee.",
                "The person who owns the blue house lives next to the one who keeps cats.",
                "The green house is immediately to the right of the white house."
            ),
            questions = listOf(
                "Who drinks coffee?",
                "Who keeps cats?",
                "Which house is green?"
            ),
            answers = listOf(
                "John",
                "Alice",
                "Green House"
            )
        )
        // Add more puzzles as needed
    )

    /**
     * Generates a Logic Grid Puzzle.
     * @return A LogicGridPuzzle object.
     */
    suspend fun generatePuzzle(): LogicGridPuzzle? = withContext(Dispatchers.Default) {
        puzzles.randomOrNull()
    }
}
