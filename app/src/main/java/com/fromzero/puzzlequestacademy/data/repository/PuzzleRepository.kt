// PuzzleRepository.kt
package com.fromzero.puzzlequestacademy.data.repository

import com.fromzero.puzzlequestacademy.data.generators.*
import com.fromzero.puzzlequestacademy.data.local.PuzzleDao
import com.fromzero.puzzlequestacademy.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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

    // Hints
    suspend fun getUserHints(): Int = withContext(Dispatchers.IO) {
        val userId = firebaseRepository.auth.currentUser?.uid ?: return@withContext 0
        firebaseRepository.getUserHints(userId)
    }

    suspend fun updateUserHints(newHints: Int) = withContext(Dispatchers.IO) {
        val userId = firebaseRepository.auth.currentUser?.uid ?: return@withContext
        firebaseRepository.updateUserHints(userId, newHints)
    }

    // Puzzle Generation
    suspend fun generateAnagramPuzzle(): AnagramPuzzle? = withContext(Dispatchers.IO) {
        anagramGenerator.generateAnagram()
    }
    suspend fun generateAlgebraProblem(): AlgebraProblem? = withContext(Dispatchers.IO) {
        algebraGenerator.generateProblem()
    }
    suspend fun generateBiologyMatchingPuzzle(): BiologyMatchingPuzzle? = withContext(Dispatchers.IO) {
        biologyMatchingGenerator.generatePuzzle()
    }
    suspend fun generateCapitalMatchingPuzzle(): CapitalMatchingPuzzle? = withContext(Dispatchers.IO) {
        capitalCityMatchingGenerator.generatePuzzle()
    }
    suspend fun generateChemistryEquation(): ChemistryEquation? = withContext(Dispatchers.IO) {
        chemistryEquationBalancer.balanceEquation("H2 + O2 -> H2O")
    }
    suspend fun generateCrosswordPuzzle(): CrosswordPuzzle? = withContext(Dispatchers.IO) {
        crosswordGenerator.generatePuzzle()
    }
    suspend fun generateEnvironmentalScenario(): EnvironmentalScenario? = withContext(Dispatchers.IO) {
        environmentalScenarioGenerator.generateScenario()
    }
    suspend fun generateFlagIdentificationPuzzle(): FlagIdentificationPuzzle? = withContext(Dispatchers.IO) {
        flagIdentificationGenerator.generatePuzzle()
    }
    suspend fun generateGeometryProblem(): GeometryProblem? = withContext(Dispatchers.IO) {
        geometryProblemGenerator.generateProblem()
    }
    suspend fun generateLogicGridPuzzle(): LogicGridPuzzle? = withContext(Dispatchers.IO) {
        logicGridGenerator.generatePuzzle()
    }
    suspend fun generatePatternProblem(): PatternProblem? = withContext(Dispatchers.IO) {
        patternRecognitionGenerator.generateProblem()
    }
    suspend fun generatePhysicsProblem(): PhysicsProblem? = withContext(Dispatchers.IO) {
        physicsKinematicsProblemGenerator.generateProblem()
    }
    suspend fun generateSudokuPuzzle(): SudokuPuzzle? = withContext(Dispatchers.IO) {
        sudokuGenerator.generatePuzzle()
    }
    suspend fun generateWordSearchPuzzle(): WordSearchPuzzle? = withContext(Dispatchers.IO) {
        wordSearchGenerator.generatePuzzle()
    }

    suspend fun updatePuzzleProgress(puzzleType: String, isCompleted: Boolean) = withContext(Dispatchers.IO) {
        firebaseRepository.updatePuzzleProgress(puzzleType, isCompleted)
    }
}
