// ui/puzzles/biology/BiologyMatchingViewModel.kt

package com.fromzero.puzzlequestacademy.ui.puzzles.biology

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fromzero.puzzlequestacademy.data.model.BiologyMatchingPuzzle
import com.fromzero.puzzlequestacademy.data.repository.PuzzleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BiologyMatchingViewModel @Inject constructor(
    private val repository: PuzzleRepository
) : ViewModel() {

    private val _puzzle = MutableLiveData<BiologyMatchingPuzzle?>()
    val puzzle: LiveData<BiologyMatchingPuzzle?> = _puzzle

    private val _feedback = MutableLiveData<String?>()
    val feedback: LiveData<String?> = _feedback

    init {
        generatePuzzle()
    }

    fun generatePuzzle() {
        viewModelScope.launch {
            val biologyPuzzle = repository.generateBiologyMatchingPuzzle()
            _puzzle.value = biologyPuzzle
        }
    }

    suspend fun submitMatches(userMatches: List<String>) {
        val correctMatches = _puzzle.value?.matches
        if (correctMatches != null && userMatches.size == correctMatches.size) {
            if (userMatches.zip(correctMatches).all { it.first.equals(it.second, ignoreCase = true) }) {
                _feedback.value = "All matches are correct!"
                repository.updatePuzzleProgress("BiologyMatching", true)
                generatePuzzle()
            } else {
                _feedback.value = "Some matches are incorrect. Try again!"
            }
        } else {
            _feedback.value = "Incomplete matches."
        }
    }

    fun useHint() {
        viewModelScope.launch {
            val currentHints = repository.getUserHints()
            if (currentHints >= 10) { // Example cost
                repository.updateUserHints(currentHints - 10)
                // Provide a hint, e.g., reveal one correct match
                _feedback.value = "Hint: One of your matches is correct."
            } else {
                _feedback.value = "Not enough hints. Purchase more!"
            }
        }
    }

    fun clearFeedback() {
        _feedback.value = null
    }
}
