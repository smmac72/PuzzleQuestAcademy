// PuzzleDatabase.kt

package com.fromzero.puzzlequestacademy.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.fromzero.puzzlequestacademy.data.model.UserProfile

@Database(entities = [UserProfile::class], version = 1, exportSchema = false)
abstract class PuzzleDatabase : RoomDatabase() {

    abstract fun puzzleDao(): PuzzleDao

    companion object {
        @Volatile
        private var INSTANCE: PuzzleDatabase? = null

        fun getDatabase(context: Context): PuzzleDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PuzzleDatabase::class.java,
                    "puzzle_database"
                )
                    .fallbackToDestructiveMigration() // Handle migrations properly in production
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
