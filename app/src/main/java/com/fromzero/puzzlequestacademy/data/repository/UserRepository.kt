// UserRepository.kt

package com.fromzero.puzzlequestacademy.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.fromzero.puzzlequestacademy.data.local.PuzzleDao
import com.fromzero.puzzlequestacademy.data.model.UserProfile
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val puzzleDao: PuzzleDao,
    private val firestore: FirebaseFirestore
) {

    // Inserts or updates a UserProfile in Room and Firebase
    suspend fun insertUserProfile(userProfile: UserProfile) {
        // Insert into Room
        puzzleDao.insertUserProfile(userProfile)

        // Insert into Firebase Firestore
        firestore.collection("users").document(userProfile.userId).set(userProfile).await()
    }

    // Retrieves a UserProfile from Room; if not found, fetches from Firebase and caches it
    suspend fun getUserProfile(userId: String): UserProfile? {
        // Try fetching from Room
        val cachedProfile = puzzleDao.getUserProfile(userId)
        if (cachedProfile != null) {
            return cachedProfile
        }

        // Fetch from Firebase Firestore
        val snapshot = firestore.collection("users").document(userId).get().await()
        val fetchedProfile = snapshot.toObject(UserProfile::class.java)

        // Cache in Room if fetched successfully
        if (fetchedProfile != null) {
            puzzleDao.insertUserProfile(fetchedProfile)
        }

        return fetchedProfile
    }
}
