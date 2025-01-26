// ui/puzzles/sudoku/SudokuViewModel.kt

package com.fromzero.puzzlequestacademy.ui.puzzles.sudoku

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fromzero.puzzlequestacademy.data.model.SudokuPuzzle
import com.fromzero.puzzlequestacademy.data.repository.PuzzleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SudokuViewModel @Inject constructor(
    private val repository: PuzzleRepository
) : ViewModel() {

    private val _puzzle = MutableLiveData<SudokuPuzzle?>()
    val puzzle: LiveData<SudokuPuzzle?> = _puzzle

    private val _feedback = MutableLiveData<String?>()
    val feedback: LiveData<String?> = _feedback

    init {
        generatePuzzle()
    }

    fun generatePuzzle() {
        viewModelScope.launch {
            val sudokuPuzzle = repository.generateSudokuPuzzle()
            _puzzle.value = sudokuPuzzle
        }
    }

    suspend fun submitAnswer(submittedGrid: Array<Array<Int?>>) {
        _feedback.value = "test sudoku."
        /*
        val solution = _puzzle.value?.solution
        if (solution != null) {
            if (compareGrids(submittedGrid, solution)) {
                _feedback.value = "Correct!"
                repository.updatePuzzleProgress("Sudoku", true)
                generatePuzzle()
            } else {
                _feedback.value = "Incorrect. Check your answers."
            }
        }*/
    }

    private fun compareGrids(submitted: Array<Array<Int?>>, solution: Array<Array<Int>>): Boolean {
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                if (submitted[i][j] != solution[i][j]) return false
            }
        }
        return true
    }

    fun useHint() {
        viewModelScope.launch {
            val currentHints = repository.getUserHints()
            if (currentHints >= 20) { // Example cost
                repository.updateUserHints(currentHints - 20)
                val currentPuzzle = _puzzle.value
                if (currentPuzzle != null) {
                    // Provide a hint, e.g., reveal a cell
                    _feedback.value = "Hint: A cell has been revealed."
                    // Implement actual hint logic as needed
                }
            } else {
                _feedback.value = "Not enough hints. Purchase more!"
            }
        }
    }

    fun clearFeedback() {
        _feedback.value = null
    }
}
