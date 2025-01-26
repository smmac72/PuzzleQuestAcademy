// data/generators/FlagIdentificationGenerator.kt

package com.fromzero.puzzlequestacademy.data.generators

import com.fromzero.puzzlequestacademy.data.model.FlagIdentificationPuzzle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class FlagIdentificationGenerator {

    private val countriesAndFlags = mapOf(
        "Canada" to "flags/canada_flag.png",
        "Brazil" to "flags/brazil_flag.png",
        "Germany" to "flags/germany_flag.png",
        "Australia" to "flags/australia_flag.png",
        "Japan" to "flags/japan_flag.png"
    )

    /**
     * Generates a Flag Identification Puzzle.
     * @return A FlagIdentificationPuzzle object.
     */
    suspend fun generatePuzzle(): FlagIdentificationPuzzle? = withContext(Dispatchers.Default) {
        val country = countriesAndFlags.keys.randomOrNull() ?: return@withContext null
        val flagPath = countriesAndFlags[country] ?: ""
        FlagIdentificationPuzzle(countryName = country, flagImagePath = flagPath)
    }
}
