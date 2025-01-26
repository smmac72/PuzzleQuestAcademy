// repository/PuzzleRepository.kt

package com.fromzero.puzzlequestacademy.data.repository

import com.fromzero.puzzlequestacademy.data.generators.*
import com.fromzero.puzzlequestacademy.data.local.PuzzleDao
import com.fromzero.puzzlequestacademy.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PuzzleRepository @Inject constructor(
    val firebaseRepository: FirebaseRepository,
    private val puzzleDao: PuzzleDao,
    private val algebraGenerator: AlgebraProblemGenerator,
    private val anagramGenerator: AnagramGenerator,
    private val biologyMatchingGenerator: BiologyMatchingGenerator,
    private val capitalCityMatchingGenerator: CapitalCityMatchingGenerator,
    private val chemistryEquationBalancer: ChemistryEquationBalancer,
    private val crosswordGenerator: CrosswordGenerator,
    private val environmentalScenarioGenerator: EnvironmentalScenarioGenerator,
    private val flagIdentificationGenerator: FlagIdentificationGenerator,
    private val geometryProblemGenerator: GeometryProblemGenerator,
    private val logicGridGenerator: LogicGridGenerator,
    private val patternRecognitionGenerator: PatternRecognitionGenerator,
    private val physicsKinematicsProblemGenerator: PhysicsKinematicsProblemGenerator,
    private val sudokuGenerator: SudokuGenerator,
    private val wordSearchGenerator: WordSearchGenerator
) {

    // User Profile Methods
    suspend fun getUserProfile(userId: String): UserProfile? = withContext(Dispatchers.IO) {
        puzzleDao.getUserProfile(userId) ?: firebaseRepository.getUserProfile(userId)?.also {
            puzzleDao.insertUserProfile(it)
        }
    }

    suspend fun updateUserProfile(userProfile: UserProfile) = withContext(Dispatchers.IO) {
        puzzleDao.insertUserProfile(userProfile)
        firebaseRepository.updateUserProfile(userProfile)
    }

    // Hints Management
    suspend fun getUserHints(): Int = withContext(Dispatchers.IO) {
        val userId = firebaseRepository.auth.currentUser?.uid ?: return@withContext 0
        firebaseRepository.getUserHints(userId)
    }

    suspend fun updateUserHints(newHints: Int) = withContext(Dispatchers.IO) {
        val userId = firebaseRepository.auth.currentUser?.uid ?: return@withContext
        firebaseRepository.updateUserHints(userId, newHints)
    }

    // Puzzle Generation Methods

    // Anagram Puzzle
    suspend fun generateAnagramPuzzle(): AnagramPuzzle? = withContext(Dispatchers.IO) {
        anagramGenerator.generateAnagram()
    }

    // Algebra Problem
    suspend fun generateAlgebraProblem(): AlgebraProblem? = withContext(Dispatchers.IO) {
        algebraGenerator.generateProblem()
    }

    // Biology Matching Puzzle
    suspend fun generateBiologyMatchingPuzzle(): BiologyMatchingPuzzle? = withContext(Dispatchers.IO) {
        biologyMatchingGenerator.generatePuzzle()
    }

    // Capital City Matching Puzzle
    suspend fun generateCapitalMatchingPuzzle(): CapitalMatchingPuzzle? = withContext(Dispatchers.IO) {
        capitalCityMatchingGenerator.generatePuzzle()
    }

    // Chemistry Equation Balancer
    suspend fun generateChemistryEquation(): ChemistryEquation? = withContext(Dispatchers.IO) {
        chemistryEquationBalancer.balanceEquation("H2 + O2 -> H2O") // Example equation
    }

    // Crossword Puzzle
    suspend fun generateCrosswordPuzzle(): CrosswordPuzzle? = withContext(Dispatchers.IO) {
        crosswordGenerator.generatePuzzle()
    }

    // Environmental Scenario
    suspend fun generateEnvironmentalScenario(): EnvironmentalScenario? = withContext(Dispatchers.IO) {
        environmentalScenarioGenerator.generateScenario()
    }

    // Flag Identification Puzzle
    suspend fun generateFlagIdentificationPuzzle(): FlagIdentificationPuzzle? = withContext(Dispatchers.IO) {
        flagIdentificationGenerator.generatePuzzle()
    }

    // Geometry Problem
    suspend fun generateGeometryProblem(): GeometryProblem? = withContext(Dispatchers.IO) {
        geometryProblemGenerator.generateProblem()
    }

    // Logic Grid Puzzle
    suspend fun generateLogicGridPuzzle(): LogicGridPuzzle? = withContext(Dispatchers.IO) {
        logicGridGenerator.generatePuzzle()
    }

    // Pattern Recognition Problem
    suspend fun generatePatternProblem(): PatternProblem? = withContext(Dispatchers.IO) {
        patternRecognitionGenerator.generateProblem()
    }

    // Physics Kinematics Problem
    suspend fun generatePhysicsProblem(): PhysicsProblem? = withContext(Dispatchers.IO) {
        physicsKinematicsProblemGenerator.generateProblem()
    }

    // Sudoku Puzzle
    suspend fun generateSudokuPuzzle(): SudokuPuzzle? = withContext(Dispatchers.IO) {
        sudokuGenerator.generatePuzzle()
    }

    // Word Search Puzzle
    suspend fun generateWordSearchPuzzle(): WordSearchPuzzle? = withContext(Dispatchers.IO) {
        wordSearchGenerator.generatePuzzle()
    }

    // Update Puzzle Progress
    suspend fun updatePuzzleProgress(puzzleType: String, isCompleted: Boolean) = withContext(Dispatchers.IO) {
        firebaseRepository.updatePuzzleProgress(puzzleType, isCompleted)
    }
}
