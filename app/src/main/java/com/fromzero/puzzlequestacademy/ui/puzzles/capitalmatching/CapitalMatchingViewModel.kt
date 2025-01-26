// ui/puzzles/capitalmatching/CapitalMatchingViewModel.kt

package com.fromzero.puzzlequestacademy.ui.puzzles.capitalmatching

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fromzero.puzzlequestacademy.data.model.CapitalMatchingPuzzle
import com.fromzero.puzzlequestacademy.data.repository.PuzzleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CapitalMatchingViewModel @Inject constructor(
    private val repository: PuzzleRepository
) : ViewModel() {

    private val _puzzle = MutableLiveData<CapitalMatchingPuzzle?>()
    val puzzle: LiveData<CapitalMatchingPuzzle?> = _puzzle

    private val _feedback = MutableLiveData<String?>()
    val feedback: LiveData<String?> = _feedback

    init {
        generatePuzzle()
    }

    fun generatePuzzle() {
        viewModelScope.launch {
            val capitalPuzzle = repository.generateCapitalMatchingPuzzle()
            _puzzle.value = capitalPuzzle
        }
    }

    suspend fun submitAnswer(answer: String) {
        val correctAnswer = _puzzle.value?.capital?.uppercase()
        if (correctAnswer != null) {
            if (answer.uppercase() == correctAnswer) {
                _feedback.value = "Correct!"
                repository.updatePuzzleProgress("CapitalMatching", true)
                generatePuzzle()
            } else {
                _feedback.value = "Incorrect. Try again!"
            }
        }
    }

    fun useHint() {
        viewModelScope.launch {
            val currentHints = repository.getUserHints()
            if (currentHints >= 5) { // Example cost
                repository.updateUserHints(currentHints - 5)
                val currentPuzzle = _puzzle.value
                if (currentPuzzle != null) {
                    // Provide a hint, e.g., reveal the first letter of the capital
                    _feedback.value = "Hint: The capital starts with '${currentPuzzle.capital[0]}'"
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
