// data/generators/SudokuGenerator.kt

package com.fromzero.puzzlequestacademy.data.generators

import com.fromzero.puzzlequestacademy.data.model.SudokuPuzzle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SudokuGenerator {

    private val puzzles = listOf(
        SudokuPuzzle(
            grid = mutableListOf(
                mutableListOf(5, 3, null, null, 7, null, null, null, null),
                mutableListOf(6, null, null, 1, 9, 5, null, null, null),
                mutableListOf(null, 9, 8, null, null, null, null, 6, null),
                mutableListOf(8, null, null, null, 6, null, null, null, 3),
                mutableListOf(4, null, null, 8, null, 3, null, null, 1),
                mutableListOf(7, null, null, null, 2, null, null, null, 6),
                mutableListOf(null, 6, null, null, null, null, 2, 8, null),
                mutableListOf(null, null, null, 4, 1, 9, null, null, 5),
                mutableListOf(null, null, null, null, 8, null, null, 7, 9)
            ),
            solution = mutableListOf(
                mutableListOf(5, 3, 4, 6, 7, 8, 9, 1, 2),
                mutableListOf(6, 7, 2, 1, 9, 5, 3, 4, 8),
                mutableListOf(1, 9, 8, 3, 4, 2, 5, 6, 7),
                mutableListOf(8, 5, 9, 7, 6, 1, 4, 2, 3),
                mutableListOf(4, 2, 6, 8, 5, 3, 7, 9, 1),
                mutableListOf(7, 1, 3, 9, 2, 4, 8, 5, 6),
                mutableListOf(9, 6, 1, 5, 3, 7, 2, 8, 4),
                mutableListOf(2, 8, 7, 4, 1, 9, 6, 3, 5),
                mutableListOf(3, 4, 5, 2, 8, 6, 1, 7, 9)
            )
        )
        // Add more Sudoku puzzles as needed
    )

    /**
     * Generates a Sudoku Puzzle.
     * @return A SudokuPuzzle object.
     */
    suspend fun generatePuzzle(): SudokuPuzzle? = withContext(Dispatchers.Default) {
        puzzles.randomOrNull()
    }
}
