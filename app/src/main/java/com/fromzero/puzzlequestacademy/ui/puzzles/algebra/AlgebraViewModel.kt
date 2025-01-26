// ui/puzzles/algebra/AlgebraViewModel.kt

package com.fromzero.puzzlequestacademy.ui.puzzles.algebra

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fromzero.puzzlequestacademy.data.model.AlgebraProblem
import com.fromzero.puzzlequestacademy.data.repository.PuzzleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlgebraViewModel @Inject constructor(
    private val repository: PuzzleRepository
) : ViewModel() {

    private val _problem = MutableLiveData<AlgebraProblem?>()
    val problem: LiveData<AlgebraProblem?> = _problem

    private val _feedback = MutableLiveData<String?>()
    val feedback: LiveData<String?> = _feedback

    init {
        generateProblem()
    }

    fun generateProblem() {
        viewModelScope.launch {
            val algebraProblem = repository.generateAlgebraProblem()
            _problem.value = algebraProblem
        }
    }

    suspend fun submitAnswer(answer: Double) {
        val correctAnswer = _problem.value?.solution
        if (correctAnswer != null) {
            if (answer == correctAnswer) {
                _feedback.value = "Correct!"
                repository.updatePuzzleProgress("Algebra", true)
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
                    // Provide a hint, e.g., reveal a step
                    _feedback.value = "Hint: Solve for the variable by isolating it."
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
