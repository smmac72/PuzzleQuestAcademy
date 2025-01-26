// ui/puzzles/pattern/PatternRecognitionViewModel.kt

package com.fromzero.puzzlequestacademy.ui.puzzles.pattern

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fromzero.puzzlequestacademy.data.model.PatternProblem
import com.fromzero.puzzlequestacademy.data.repository.PuzzleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatternRecognitionViewModel @Inject constructor(
    private val repository: PuzzleRepository
) : ViewModel() {

    private val _problem = MutableLiveData<PatternProblem?>()
    val problem: LiveData<PatternProblem?> = _problem

    private val _feedback = MutableLiveData<String?>()
    val feedback: LiveData<String?> = _feedback

    init {
        generateProblem()
    }

    fun generateProblem() {
        viewModelScope.launch {
            val patternProblem = repository.generatePatternProblem()
            _problem.value = patternProblem
        }
    }

    suspend fun submitAnswer(answer: Int) {
        val correctAnswer = _problem.value?.nextElement
        if (correctAnswer != null) {
            if (answer == correctAnswer) {
                _feedback.value = "Correct!"
                repository.updatePuzzleProgress("PatternRecognition", true)
                generateProblem()
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
                val currentProblem = _problem.value
                if (currentProblem != null) {
                    // Provide a hint, e.g., reveal the pattern rule
                    _feedback.value = "Hint: The pattern increases by a constant difference."
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
