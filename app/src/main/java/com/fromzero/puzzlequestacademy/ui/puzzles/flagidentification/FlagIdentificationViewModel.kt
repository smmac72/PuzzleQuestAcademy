// ui/puzzles/flagidentification/FlagIdentificationViewModel.kt

package com.fromzero.puzzlequestacademy.ui.puzzles.flagidentification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fromzero.puzzlequestacademy.data.model.FlagIdentificationPuzzle
import com.fromzero.puzzlequestacademy.data.repository.PuzzleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlagIdentificationViewModel @Inject constructor(
    private val repository: PuzzleRepository
) : ViewModel() {

    private val _puzzle = MutableLiveData<FlagIdentificationPuzzle?>()
    val puzzle: LiveData<FlagIdentificationPuzzle?> = _puzzle

    private val _feedback = MutableLiveData<String?>()
    val feedback: LiveData<String?> = _feedback

    init {
        generatePuzzle()
    }

    fun generatePuzzle() {
        viewModelScope.launch {
            val flagPuzzle = repository.generateFlagIdentificationPuzzle()
            _puzzle.value = flagPuzzle
        }
    }

    suspend fun submitAnswer(answer: String) {
        val correctAnswer = _puzzle.value?.countryName?.uppercase()
        if (correctAnswer != null) {
            if (answer.uppercase() == correctAnswer) {
                _feedback.value = "Correct!"
                repository.updatePuzzleProgress("FlagIdentification", true)
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
                    // Provide a hint, e.g., reveal the continent
                    _feedback.value = "Hint: The country is in Europe."
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
