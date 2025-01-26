// data/generators/EnvironmentalScenarioGenerator.kt

package com.fromzero.puzzlequestacademy.data.generators

import com.fromzero.puzzlequestacademy.data.model.EnvironmentalScenario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class EnvironmentalScenarioGenerator {

    private val scenarios = listOf(
        EnvironmentalScenario(
            scenarioDescription = "A factory is releasing pollutants into the river, affecting the aquatic life.",
            question = "What is the primary type of pollution caused by the factory?",
            correctAnswer = "Water Pollution"
        ),
        EnvironmentalScenario(
            scenarioDescription = "Deforestation in the Amazon rainforest is leading to loss of biodiversity.",
            question = "What is the most significant impact of deforestation?",
            correctAnswer = "Habitat Destruction"
        ),
        EnvironmentalScenario(
            scenarioDescription = "Increased use of plastic is resulting in oceanic plastic waste.",
            question = "What is a major consequence of plastic waste in oceans?",
            correctAnswer = "Harm to Marine Life"
        )
    )

    /**
     * Generates an Environmental Scenario Puzzle.
     * @return An EnvironmentalScenario object.
     */
    suspend fun generateScenario(): EnvironmentalScenario? = withContext(Dispatchers.Default) {
        scenarios.randomOrNull()
    }
}
