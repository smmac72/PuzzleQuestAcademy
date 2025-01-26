// ui/puzzles/anagram/AnagramViewModel.kt

package com.fromzero.puzzlequestacademy.ui.puzzles.anagram

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fromzero.puzzlequestacademy.data.model.AnagramPuzzle
import com.fromzero.puzzlequestacademy.data.repository.PuzzleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnagramViewModel @Inject constructor(
    private val repository: PuzzleRepository
) : ViewModel() {

    private val _puzzle = MutableLiveData<AnagramPuzzle?>()
    val puzzle: LiveData<AnagramPuzzle?> = _puzzle

    private val _feedback = MutableLiveData<String?>()
    val feedback: LiveData<String?> = _feedback

    init {
        generatePuzzle()
    }

    fun generatePuzzle() {
        viewModelScope.launch {
            val anagram = repository.generateAnagramPuzzle()
            _puzzle.value = anagram
        }
    }

    suspend fun submitAnswer(answer: String) {
        val correctAnswer = _puzzle.value?.correctWord?.uppercase()
        if (correctAnswer != null) {
            if (answer.uppercase() == correctAnswer) {
                _feedback.value = "Correct!"
                repository.updatePuzzleProgress("Anagram", true)
                generatePuzzle()
            } else {
                _feedback.value = "Incorrect. Try again!"
            }
        }
    }

    fun useHint() {
        viewModelScope.launch {
            val currentHints = repository.getUserHints()
            if (currentHints >= 10) { // Example cost
                repository.updateUserHints(currentHints - 10)
                val currentPuzzle = _puzzle.value
                if (currentPuzzle != null) {
                    // Provide a hint, e.g., reveal the first letter
                    _feedback.value = "Hint: The first letter is '${currentPuzzle.correctWord[0]}'"
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
