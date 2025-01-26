// data/generators/WordSearchGenerator.kt

package com.fromzero.puzzlequestacademy.data.generators

import com.fromzero.puzzlequestacademy.data.model.WordSearchPuzzle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class WordSearchGenerator {

    private val words = listOf(
        "COMPOSE", "ANDROID", "FIREBASE", "HILT", "KOTLIN",
        "NAVIGATION", "ROOM", "DEPENDENCY", "INJECTION", "VIEWMODEL"
    )

    /**
     * Generates a Word Search Puzzle.
     * @return A WordSearchPuzzle object.
     */
    suspend fun generatePuzzle(): WordSearchPuzzle = withContext(Dispatchers.Default) {
        // Placeholder for actual word search generation logic
        // For simplicity, returning a predefined grid
        val grid = arrayOf(
            arrayOf('C', 'O', 'M', 'P', 'O', 'S', 'E', 'A', 'B', 'C'),
            arrayOf('A', 'N', 'D', 'R', 'O', 'I', 'D', 'D', 'E', 'F'),
            arrayOf('F', 'I', 'R', 'E', 'B', 'A', 'S', 'E', 'G', 'H'),
            arrayOf('H', 'I', 'L', 'T', 'I', 'J', 'K', 'L', 'M', 'N'),
            arrayOf('K', 'O', 'T', 'L', 'I', 'N', 'O', 'P', 'Q', 'R'),
            arrayOf('N', 'A', 'V', 'I', 'G', 'A', 'T', 'I', 'O', 'N'),
            arrayOf('R', 'O', 'O', 'M', 'S', 'T', 'U', 'V', 'W', 'X'),
            arrayOf('D', 'E', 'P', 'E', 'N', 'D', 'E', 'N', 'C', 'Y'),
            arrayOf('I', 'N', 'J', 'E', 'C', 'T', 'I', 'O', 'N', 'Z'),
            arrayOf('V', 'I', 'E', 'W', 'M', 'O', 'D', 'E', 'L', 'A')
        )
        WordSearchPuzzle(grid = grid, words = words)
    }
}
