// ui/puzzles/crossword/CrosswordViewModel.kt

package com.fromzero.puzzlequestacademy.ui.puzzles.crossword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fromzero.puzzlequestacademy.data.model.CrosswordPuzzle
import com.fromzero.puzzlequestacademy.data.repository.PuzzleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CrosswordViewModel @Inject constructor(
    private val repository: PuzzleRepository
) : ViewModel() {

    private val _puzzle = MutableLiveData<CrosswordPuzzle?>()
    val puzzle: LiveData<CrosswordPuzzle?> = _puzzle

    private val _feedback = MutableLiveData<String?>()
    val feedback: LiveData<String?> = _feedback

    init {
        generatePuzzle()
    }

    fun generatePuzzle() {
        viewModelScope.launch {
            val crosswordPuzzle = repository.generateCrosswordPuzzle()
            _puzzle.value = crosswordPuzzle
        }
    }

    fun submitPuzzle() {
        // Placeholder for submission logic
        // Implement validation based on user inputs
        _feedback.value = "Submission functionality not implemented yet."
    }

    fun useHint() {
        viewModelScope.launch {
            val currentHints = repository.getUserHints()
            if (currentHints >= 10) { // Example cost
                repository.updateUserHints(currentHints - 10)
                // Provide a hint, e.g., reveal a letter
                _feedback.value = "Hint: The first letter of the first word is 'K'."
            } else {
                _feedback.value = "Not enough hints. Purchase more!"
            }
        }
    }

    fun clearFeedback() {
        _feedback.value = null
    }
}
