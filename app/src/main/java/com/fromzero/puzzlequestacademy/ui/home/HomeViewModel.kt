// viewmodel/HomeViewModel.kt

package com.fromzero.puzzlequestacademy.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fromzero.puzzlequestacademy.R
import com.fromzero.puzzlequestacademy.data.model.UserProfile
import com.fromzero.puzzlequestacademy.data.repository.PuzzleRepository
import com.fromzero.puzzlequestacademy.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PuzzleRepository
) : ViewModel() {

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile.asStateFlow()

    private val _hints = MutableStateFlow(0)
    val hints: StateFlow<Int> = _hints.asStateFlow()

    // Define available puzzles
    val puzzles = listOf(
        Puzzle("Anagram", Screen.Anagram.route, R.drawable.ic_anagram),
        Puzzle("Sudoku", Screen.Sudoku.route, R.drawable.ic_sudoku),
        Puzzle("Crossword", Screen.Crossword.route, R.drawable.ic_crossword),
        Puzzle("Word Search", Screen.WordSearch.route, R.drawable.ic_wordsearch),
        Puzzle("Algebra", Screen.Algebra.route, R.drawable.ic_algebra),
        Puzzle("Geometry", Screen.Geometry.route, R.drawable.ic_geometry),
        Puzzle("Pattern Recognition", Screen.PatternRecognition.route, R.drawable.ic_pattern),
        Puzzle("Logic Grid", Screen.LogicGrid.route, R.drawable.ic_logicgrid),
        Puzzle("Biology Matching", Screen.BiologyMatching.route, R.drawable.ic_biology),
        Puzzle("Chemistry Equation", Screen.ChemistryEquation.route, R.drawable.ic_chemistry),
        Puzzle("Physics Problem", Screen.PhysicsProblem.route, R.drawable.ic_physics),
        Puzzle("Environmental Scenario", Screen.EnvironmentalScenario.route, R.drawable.ic_environmental),
        Puzzle("Flag Identification", Screen.FlagIdentification.route, R.drawable.ic_flagidentification),
        Puzzle("Capital Matching", Screen.CapitalMatching.route, R.drawable.ic_capitalmatching)
        // Add other puzzles similarly
    )

    init {
        viewModelScope.launch {
            // Assume user is already authenticated
            val currentUser = repository.firebaseRepository.auth.currentUser
            currentUser?.let { user ->
                repository.getUserProfile(user.uid)?.let { profile ->
                    _userProfile.value = profile
                    _hints.value = profile.hints
                }
            }
        }
    }
    fun signOut() {
        viewModelScope.launch {
            repository.firebaseRepository.auth.signOut()
            // todo
        }
    }

    data class Puzzle(
        val name: String,
        val route: String,
        val iconRes: Int
    )
}
