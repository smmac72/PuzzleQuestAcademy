// ui/puzzles/chemistry/ChemistryEquationViewModel.kt

package com.fromzero.puzzlequestacademy.ui.puzzles.chemistry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fromzero.puzzlequestacademy.data.model.ChemistryEquation
import com.fromzero.puzzlequestacademy.data.repository.PuzzleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChemistryEquationViewModel @Inject constructor(
    private val repository: PuzzleRepository
) : ViewModel() {

    private val _equation = MutableLiveData<ChemistryEquation?>()
    val equation: LiveData<ChemistryEquation?> = _equation

    private val _feedback = MutableLiveData<String?>()
    val feedback: LiveData<String?> = _feedback

    init {
        generateEquation()
    }

    fun generateEquation() {
        viewModelScope.launch {
            val chemEquation = repository.generateChemistryEquation()
            _equation.value = chemEquation
        }
    }

    suspend fun submitAnswer(answer: String) {
        val correctAnswer = _equation.value?.balancedEquation?.replace(" ", "")
        if (correctAnswer != null) {
            if (answer.replace(" ", "").equals(correctAnswer, ignoreCase = true)) {
                _feedback.value = "Correct!"
                repository.updatePuzzleProgress("ChemistryEquation", true)
                generateEquation()
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
                val currentEquation = _equation.value
                if (currentEquation != null) {
                    // Provide a hint, e.g., reveal the coefficient of a specific element
                    _feedback.value = "Hint: The coefficient of Oxygen is 2."
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
