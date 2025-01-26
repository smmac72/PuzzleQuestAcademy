// ui/puzzles/physics/PhysicsProblemViewModel.kt

package com.fromzero.puzzlequestacademy.ui.puzzles.physics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fromzero.puzzlequestacademy.data.model.PhysicsProblem
import com.fromzero.puzzlequestacademy.data.repository.PuzzleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhysicsProblemViewModel @Inject constructor(
    private val repository: PuzzleRepository
) : ViewModel() {

    private val _problem = MutableLiveData<PhysicsProblem?>()
    val problem: LiveData<PhysicsProblem?> = _problem

    private val _feedback = MutableLiveData<String?>()
    val feedback: LiveData<String?> = _feedback

    init {
        generateProblem()
    }

    fun generateProblem() {
        viewModelScope.launch {
            val physicsProblem = repository.generatePhysicsProblem()
            _problem.value = physicsProblem
        }
    }

    suspend fun submitAnswer(answer: Double) {
        val correctAnswer = _problem.value?.solution
        if (correctAnswer != null) {
            if (answer == correctAnswer) {
                _feedback.value = "Correct!"
                repository.updatePuzzleProgress("PhysicsKinematics", true)
                generateProblem()
            } else {
                _feedback.value = "Incorrect. Try again!"
            }
        }
    }

    fun useHint() {
        viewModelScope.launch {
            val currentHints = repository.getUserHints()
            if (currentHints >= 15) { // Example cost
                repository.updateUserHints(currentHints - 15)
                val currentProblem = _problem.value
                if (currentProblem != null) {
                    // Provide a hint, e.g., formula hint
                    _feedback.value = "Hint: Remember the kinematic equation for velocity."
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
