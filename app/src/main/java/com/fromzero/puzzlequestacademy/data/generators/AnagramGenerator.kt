// data/generators/AnagramGenerator.kt

package com.fromzero.puzzlequestacademy.data.generators

import com.fromzero.puzzlequestacademy.data.model.AnagramPuzzle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class AnagramGenerator(private val dictionary: Set<String>) {

    /**
     * Generates a random anagram puzzle.
     * @return An AnagramPuzzle object containing the anagram and the original word.
     */
    suspend fun generateAnagram(): AnagramPuzzle? = withContext(Dispatchers.Default) {
        val validWords = dictionary.filter { it.length >= 4 }
        if (validWords.isEmpty()) return@withContext null

        val originalWord = validWords.random()
        val scrambledWord = scrambleWord(originalWord)
        AnagramPuzzle(scrambledWord = scrambledWord, correctWord = originalWord)
    }

    /**
     * Scrambles the letters of a word to create an anagram.
     */
    private fun scrambleWord(word: String): String {
        val letters = word.toCharArray().toMutableList()
        var scrambled: String
        do {
            scrambled = letters.shuffled().joinToString("")
        } while (scrambled.equals(word, ignoreCase = true))
        return scrambled
    }
}
