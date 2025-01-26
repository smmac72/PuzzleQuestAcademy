// data/model/SudokuPuzzle.kt

package com.fromzero.puzzlequestacademy.data.model

data class SudokuPuzzle(
    val grid: MutableList<MutableList<Int?>> = MutableList(9) { MutableList(9) { null } },
    val solution: MutableList<MutableList<Int>> = MutableList(9) { MutableList(9) { 0 } }
)
fun SudokuPuzzle.deepCopy(): SudokuPuzzle {
    return SudokuPuzzle(
        grid = this.grid.map { it.toMutableList() }.toMutableList(),
        solution = this.solution.map { it.toMutableList() }.toMutableList()
    )
}