// repository/FirebaseRepository.kt

package com.fromzero.puzzlequestacademy.data.repository

import com.fromzero.puzzlequestacademy.data.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    private val userCollection = firestore.collection("users")
    private val puzzlesCollection = firestore.collection("puzzles")

    // User Profile Methods
    suspend fun getUserProfile(userId: String): UserProfile? {
        return try {
            val snapshot = userCollection.document(userId).get().await()
            snapshot.toObject(UserProfile::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateUserProfile(userProfile: UserProfile) {
        try {
            userCollection.document(userProfile.userId).set(userProfile).await()
        } catch (e: Exception) {
            // Handle exception as needed
        }
    }

    // Hints Management
    suspend fun getUserHints(userId: String): Int {
        val profile = getUserProfile(userId)
        return profile?.hints ?: 0
    }

    suspend fun updateUserHints(userId: String, newHints: Int) {
        try {
            userCollection.document(userId).update("hints", newHints).await()
        } catch (e: Exception) {
            // Handle exception as needed
        }
    }

    // Puzzle Fetching Methods

    // Anagram Puzzles
    suspend fun getAnagramPuzzles(): List<AnagramPuzzle> {
        return try {
            val snapshot = puzzlesCollection
                .document("anagram")
                .collection("problems")
                .get()
                .await()
            snapshot.toObjects(AnagramPuzzle::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Algebra Problems
    suspend fun getAlgebraProblems(): List<AlgebraProblem> {
        return try {
            val snapshot = puzzlesCollection
                .document("algebra")
                .collection("problems")
                .get()
                .await()
            snapshot.toObjects(AlgebraProblem::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Geometry Problems
    suspend fun getGeometryProblems(): List<GeometryProblem> {
        return try {
            val snapshot = puzzlesCollection
                .document("geometry")
                .collection("problems")
                .get()
                .await()
            snapshot.toObjects(GeometryProblem::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Sudoku Puzzles
    suspend fun getSudokuPuzzles(): List<SudokuPuzzle> {
        return try {
            val snapshot = puzzlesCollection
                .document("sudoku")
                .collection("problems")
                .get()
                .await()
            snapshot.toObjects(SudokuPuzzle::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Logic Grid Puzzles
    suspend fun getLogicGridPuzzles(): List<LogicGridPuzzle> {
        return try {
            val snapshot = puzzlesCollection
                .document("logicgrid")
                .collection("problems")
                .get()
                .await()
            snapshot.toObjects(LogicGridPuzzle::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Chemistry Equations
    suspend fun getChemistryEquations(): List<ChemistryEquation> {
        return try {
            val snapshot = puzzlesCollection
                .document("chemistry")
                .collection("problems")
                .get()
                .await()
            snapshot.toObjects(ChemistryEquation::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Environmental Scenarios
    suspend fun getEnvironmentalScenarios(): List<EnvironmentalScenario> {
        return try {
            val snapshot = puzzlesCollection
                .document("environmental")
                .collection("problems")
                .get()
                .await()
            snapshot.toObjects(EnvironmentalScenario::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Flag Identification Puzzles
    suspend fun getFlagIdentificationPuzzles(): List<FlagIdentificationPuzzle> {
        return try {
            val snapshot = puzzlesCollection
                .document("flagidentification")
                .collection("problems")
                .get()
                .await()
            snapshot.toObjects(FlagIdentificationPuzzle::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Biology Matching Puzzles
    suspend fun getBiologyMatchingPuzzles(): List<BiologyMatchingPuzzle> {
        return try {
            val snapshot = puzzlesCollection
                .document("biologymatching")
                .collection("problems")
                .get()
                .await()
            snapshot.toObjects(BiologyMatchingPuzzle::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Pattern Problems
    suspend fun getPatternProblems(): List<com.fromzero.puzzlequestacademy.data.model.PatternProblem> {
        return try {
            val snapshot = puzzlesCollection
                .document("patternrecognition")
                .collection("problems")
                .get()
                .await()
            snapshot.toObjects(com.fromzero.puzzlequestacademy.data.model.PatternProblem::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Update Puzzle Progress
    suspend fun updatePuzzleProgress(puzzleType: String, isCompleted: Boolean) {
        try {
            val userId = auth.currentUser?.uid ?: return
            userCollection.document(userId).collection("progress")
                .document(puzzleType)
                .set(mapOf("isCompleted" to isCompleted))
                .await()
        } catch (e: Exception) {
            // Handle exception as needed
        }
    }
}
