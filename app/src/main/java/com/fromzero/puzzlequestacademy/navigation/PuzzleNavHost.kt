// navigation/PuzzleNavHost.kt

package com.fromzero.puzzlequestacademy.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fromzero.puzzlequestacademy.ui.auth.AuthScreen
import com.fromzero.puzzlequestacademy.ui.home.HomeScreen
import com.fromzero.puzzlequestacademy.ui.puzzles.algebra.AlgebraProblemScreen
import com.fromzero.puzzlequestacademy.ui.puzzles.anagram.AnagramScreen
import com.fromzero.puzzlequestacademy.ui.puzzles.capitalmatching.CapitalMatchingScreen
import com.fromzero.puzzlequestacademy.ui.puzzles.chemistry.ChemistryEquationScreen
import com.fromzero.puzzlequestacademy.ui.puzzles.crossword.CrosswordPuzzleScreen
import com.fromzero.puzzlequestacademy.ui.puzzles.environmental.EnvironmentalScenarioScreen
import com.fromzero.puzzlequestacademy.ui.puzzles.flagidentification.FlagIdentificationScreen
import com.fromzero.puzzlequestacademy.ui.puzzles.geometry.GeometryProblemScreen
import com.fromzero.puzzlequestacademy.ui.puzzles.logicgrid.LogicGridScreen
import com.fromzero.puzzlequestacademy.ui.puzzles.pattern.PatternRecognitionScreen
import com.fromzero.puzzlequestacademy.ui.puzzles.physics.PhysicsProblemScreen
import com.fromzero.puzzlequestacademy.ui.puzzles.sudoku.SudokuScreen
import com.fromzero.puzzlequestacademy.ui.puzzles.wordsearch.WordSearchScreen

@Composable
fun PuzzleNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Auth.route) {
        composable(Screen.Auth.route) {
            AuthScreen(navController = navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Anagram.route) {
            AnagramScreen(navController = navController)
        }
        composable(Screen.Sudoku.route) {
            SudokuScreen(navController = navController)
        }
        composable(Screen.Crossword.route) {
            CrosswordPuzzleScreen(navController = navController)
        }
        composable(Screen.WordSearch.route) {
            WordSearchScreen(navController = navController)
        }
        composable(Screen.Algebra.route) {
            AlgebraProblemScreen(navController = navController)
        }
        composable(Screen.Geometry.route) {
            GeometryProblemScreen(navController = navController)
        }
        composable(Screen.PatternRecognition.route) {
            PatternRecognitionScreen(navController = navController)
        }
        composable(Screen.LogicGrid.route) {
            LogicGridScreen(navController = navController)
        }
        composable(Screen.BiologyMatching.route) {
            // Implement BiologyMatchingScreen.kt similarly
        }
        composable(Screen.ChemistryEquation.route) {
            ChemistryEquationScreen(navController = navController)
        }
        composable(Screen.EnvironmentalScenario.route) {
            EnvironmentalScenarioScreen(navController = navController)
        }
        composable(Screen.FlagIdentification.route) {
            FlagIdentificationScreen(navController = navController)
        }
        composable(Screen.CapitalMatching.route) {
            CapitalMatchingScreen(navController = navController)
        }
        // Add other puzzles similarly
    }
}
