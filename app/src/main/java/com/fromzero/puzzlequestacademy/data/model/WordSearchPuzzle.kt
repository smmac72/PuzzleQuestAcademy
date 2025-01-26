// data/model/WordSearchPuzzle.kt

package com.fromzero.puzzlequestacademy.data.model

data class WordSearchPuzzle(
    val grid: Array<Array<Char>> = Array(10) { Array(10) { ' ' } },
    val words: List<String> = emptyList()
)
