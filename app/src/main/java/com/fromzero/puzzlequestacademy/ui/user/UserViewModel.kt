// UserViewModel.kt

package com.fromzero.puzzlequestacademy.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fromzero.puzzlequestacademy.data.model.UserProfile
import com.fromzero.puzzlequestacademy.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    // Function to add or update a user profile
    fun addUserProfile(userProfile: UserProfile) {
        viewModelScope.launch {
            userRepository.insertUserProfile(userProfile)
        }
    }

    // Function to fetch a user profile
    suspend fun fetchUserProfile(userId: String): UserProfile? {
        return userRepository.getUserProfile(userId)
    }
}
