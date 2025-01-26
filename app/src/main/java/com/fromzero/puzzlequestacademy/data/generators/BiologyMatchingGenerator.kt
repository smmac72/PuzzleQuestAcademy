// data/generators/BiologyMatchingGenerator.kt

package com.fromzero.puzzlequestacademy.data.generators

import com.fromzero.puzzlequestacademy.data.model.BiologyMatchingPuzzle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class BiologyMatchingGenerator {

    private val biologicalTerms = listOf(
        "Photosynthesis", "Mitosis", "Evolution", "Genetics",
        "Ecology", "Osmosis", "Neurons", "DNA",
        "Enzymes", "Cellular Respiration"
    )

    private val definitions = listOf(
        "Process by which green plants use sunlight to synthesize foods.",
        "A type of cell division resulting in two identical daughter cells.",
        "Change in the heritable characteristics of biological populations.",
        "Study of genes, genetic variation, and heredity in organisms.",
        "Study of interactions among organisms and their environment.",
        "Movement of water molecules through a selectively permeable membrane.",
        "Nerve cells that transmit signals in the body.",
        "Carrier of genetic information.",
        "Proteins that act as biological catalysts.",
        "Process by which cells convert glucose into energy."
    )

    /**
     * Generates a Biology Matching Puzzle.
     * @return A BiologyMatchingPuzzle object.
     */
    suspend fun generatePuzzle(): BiologyMatchingPuzzle = withContext(Dispatchers.Default) {
        val selectedTerms = biologicalTerms.shuffled().take(5)
        val selectedDefinitions = definitions.shuffled().take(5)
        BiologyMatchingPuzzle(items = selectedTerms, matches = selectedDefinitions)
    }
}
