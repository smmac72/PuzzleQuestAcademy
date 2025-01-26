// data/generators/CrosswordGenerator.kt

package com.fromzero.puzzlequestacademy.data.generators

import com.fromzero.puzzlequestacademy.data.model.CrosswordPuzzle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class CrosswordGenerator {

    private val wordsAndClues = mapOf(
        "Kotlin" to "A modern programming language targeting JVM.",
        "Android" to "A mobile operating system developed by Google.",
        "Firebase" to "A platform developed by Google for creating mobile and web applications.",
        "Compose" to "Android’s modern toolkit for building native UI.",
        "Hilt" to "A dependency injection library for Android."
    )

    /**
     * Generates a Crossword Puzzle.
     * @return A CrosswordPuzzle object.
     */
    suspend fun generatePuzzle(): CrosswordPuzzle = withContext(Dispatchers.Default) {
        val selectedWords = wordsAndClues.keys.shuffled().take(3)
        val selectedClues = selectedWords.map { wordsAndClues[it] ?: "" }
        CrosswordPuzzle(words = selectedWords, clues = selectedClues)
    }
}
