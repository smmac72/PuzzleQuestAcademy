// ui/puzzles/wordsearch/WordSearchViewModel.kt

package com.fromzero.puzzlequestacademy.ui.puzzles.wordsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fromzero.puzzlequestacademy.data.model.WordSearchPuzzle
import com.fromzero.puzzlequestacademy.data.repository.PuzzleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordSearchViewModel @Inject constructor(
    private val repository: PuzzleRepository
) : ViewModel() {

    private val _puzzle = MutableLiveData<WordSearchPuzzle?>()
    val puzzle: LiveData<WordSearchPuzzle?> = _puzzle

    private val _feedback = MutableLiveData<String?>()
    val feedback: LiveData<String?> = _feedback

    init {
        generatePuzzle()
    }

    fun generatePuzzle() {
        viewModelScope.launch {
            val wordSearchPuzzle = repository.generateWordSearchPuzzle()
            _puzzle.value = wordSearchPuzzle
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
            if (currentHints >= 20) { // Example cost
                repository.updateUserHints(currentHints - 20)
                // Provide a hint, e.g., reveal a word location
                _feedback.value = "Hint: The word 'KOTLIN' starts at row 1."
            } else {
                _feedback.value = "Not enough hints. Purchase more!"
            }
        }
    }

    fun clearFeedback() {
        _feedback.value = null
    }
}
