// di/DatabaseModule.kt
package com.fromzero.puzzlequestacademy.di

import android.content.Context
import com.fromzero.puzzlequestacademy.data.local.PuzzleDao
import com.fromzero.puzzlequestacademy.data.local.PuzzleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): PuzzleDatabase {
        return PuzzleDatabase.getDatabase(context)
    }

    @Provides
    fun providePuzzleDao(database: PuzzleDatabase): PuzzleDao {
        return database.puzzleDao()
    }
}
