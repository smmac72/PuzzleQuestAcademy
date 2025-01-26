// data/local/PuzzleDao.kt

package com.fromzero.puzzlequestacademy.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fromzero.puzzlequestacademy.data.model.UserProfile

@Dao
interface PuzzleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(userProfile: UserProfile)

    @Query("SELECT * FROM userprofile WHERE userId = :userId LIMIT 1")
    suspend fun getUserProfile(userId: String): UserProfile?
}
