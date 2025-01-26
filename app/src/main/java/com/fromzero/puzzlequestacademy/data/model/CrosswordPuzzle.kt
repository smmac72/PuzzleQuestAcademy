// data/model/CrosswordPuzzle.kt

package com.fromzero.puzzlequestacademy.data.model

data class CrosswordPuzzle(
    val words: List<String> = emptyList(),
    val clues: List<String> = emptyList()
)
