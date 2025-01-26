// data/model/UserProfile.kt

package com.fromzero.puzzlequestacademy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userprofile")
data class UserProfile(
    @PrimaryKey val userId: String,
    val username: String,
    val email: String,
    val hints: Int
    // Add other relevant fields
)
