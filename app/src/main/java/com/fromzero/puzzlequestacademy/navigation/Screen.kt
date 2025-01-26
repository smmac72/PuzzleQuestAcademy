// navigation/Screen.kt

package com.fromzero.puzzlequestacademy.navigation

sealed class Screen(val route: String) {
    object Auth : Screen("auth")
    object Home : Screen("home")
    object Anagram : Screen("anagram")
    object Sudoku : Screen("sudoku")
    object Crossword : Screen("crossword")
    object WordSearch : Screen("wordsearch")
    object Algebra : Screen("algebra")
    object Geometry : Screen("geometry")
    object PatternRecognition : Screen("pattern_recognition")
    object LogicGrid : Screen("logic_grid")
    object BiologyMatching : Screen("biology_matching")
    object ChemistryEquation : Screen("chemistry_equation")
    object PhysicsProblem : Screen("physics_problem")
    object EnvironmentalScenario : Screen("environmental_scenario")
    object FlagIdentification : Screen("flag_identification")
    object CapitalMatching : Screen("capital_matching")
    // Add other puzzles similarly
}
