// data/generators/CapitalCityMatchingGenerator.kt

package com.fromzero.puzzlequestacademy.data.generators

import com.fromzero.puzzlequestacademy.data.model.CapitalMatchingPuzzle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class CapitalCityMatchingGenerator {

    private val countriesAndCapitals = mapOf(
        "Canada" to "Ottawa",
        "Brazil" to "Brasília",
        "Germany" to "Berlin",
        "Australia" to "Canberra",
        "Japan" to "Tokyo",
        "France" to "Paris",
        "India" to "New Delhi",
        "Italy" to "Rome",
        "Spain" to "Madrid",
        "Egypt" to "Cairo"
    )

    /**
     * Generates a Capital Matching Puzzle.
     * @return A CapitalMatchingPuzzle object.
     */
    suspend fun generatePuzzle(): CapitalMatchingPuzzle = withContext(Dispatchers.Default) {
        val country = countriesAndCapitals.keys.random()
        val capital = countriesAndCapitals[country] ?: ""
        CapitalMatchingPuzzle(country = country, capital = capital)
    }
}
