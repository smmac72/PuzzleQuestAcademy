// data/model/LogicGridPuzzle.kt

package com.fromzero.puzzlequestacademy.data.model

data class LogicGridPuzzle(
    val clues: List<String> = emptyList(),
    val questions: List<String> = emptyList(),
    val answers: List<String> = emptyList() // For validation
)
