// ui/puzzles/environmental/EnvironmentalScenarioViewModel.kt

package com.fromzero.puzzlequestacademy.ui.puzzles.environmental

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fromzero.puzzlequestacademy.data.model.EnvironmentalScenario
import com.fromzero.puzzlequestacademy.data.repository.PuzzleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EnvironmentalScenarioViewModel @Inject constructor(
    private val repository: PuzzleRepository
) : ViewModel() {

    private val _scenario = MutableLiveData<EnvironmentalScenario?>()
    val scenario: LiveData<EnvironmentalScenario?> = _scenario

    private val _feedback = MutableLiveData<String?>()
    val feedback: LiveData<String?> = _feedback

    init {
        generateScenario()
    }

    fun generateScenario() {
        viewModelScope.launch {
            val envScenario = repository.generateEnvironmentalScenario()
            _scenario.value = envScenario
        }
    }

    suspend fun submitAnswer(answer: String) {
        val correctAnswer = _scenario.value?.correctAnswer?.uppercase()
        if (correctAnswer != null) {
            if (answer.uppercase() == correctAnswer) {
                _feedback.value = "Correct!"
                repository.updatePuzzleProgress("EnvironmentalScenario", true)
                generateScenario()
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
                val currentScenario = _scenario.value
                if (currentScenario != null) {
                    // Provide a hint, e.g., give a keyword
                    _feedback.value = "Hint: Think about renewable resources."
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
