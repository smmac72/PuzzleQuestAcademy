// ui/puzzles/logicgrid/LogicGridViewModel.kt

package com.fromzero.puzzlequestacademy.ui.puzzles.logicgrid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fromzero.puzzlequestacademy.data.model.LogicGridPuzzle
import com.fromzero.puzzlequestacademy.data.repository.PuzzleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogicGridViewModel @Inject constructor(
    private val repository: PuzzleRepository
) : ViewModel() {

    private val _puzzle = MutableLiveData<LogicGridPuzzle?>()
    val puzzle: LiveData<LogicGridPuzzle?> = _puzzle

    private val _feedback = MutableLiveData<String?>()
    val feedback: LiveData<String?> = _feedback

    init {
        generatePuzzle()
    }

    fun generatePuzzle() {
        viewModelScope.launch {
            val logicPuzzle = repository.generateLogicGridPuzzle()
            _puzzle.value = logicPuzzle
        }
    }

    suspend fun submitAnswers(userAnswers: List<String>) {
        val correctAnswers = _puzzle.value?.answers
        if (correctAnswers != null && userAnswers.size == correctAnswers.size) {
            if (userAnswers.zip(correctAnswers).all { it.first.equals(it.second, ignoreCase = true) }) {
                _feedback.value = "Correct!"
                repository.updatePuzzleProgress("LogicGrid", true)
                generatePuzzle()
            } else {
                _feedback.value = "Some answers are incorrect. Try again!"
            }
        } else {
            _feedback.value = "Incomplete answers."
        }
    }

    fun useHint() {
        viewModelScope.launch {
            val currentHints = repository.getUserHints()
            if (currentHints >= 15) { // Example cost
                repository.updateUserHints(currentHints - 15)
                // Provide a hint, e.g., reveal one correct answer
                _feedback.value = "Hint: One of your answers is correct."
            } else {
                _feedback.value = "Not enough hints. Purchase more!"
            }
        }
    }

    fun clearFeedback() {
        _feedback.value = null
    }
}
