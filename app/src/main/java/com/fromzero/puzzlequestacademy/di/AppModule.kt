// di/AppModule.kt

package com.fromzero.puzzlequestacademy.di

import android.content.Context
import com.fromzero.puzzlequestacademy.data.generators.*
import com.fromzero.puzzlequestacademy.data.repository.FirebaseRepository
import com.fromzero.puzzlequestacademy.data.repository.PuzzleRepository
import com.fromzero.puzzlequestacademy.data.local.PuzzleDao
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @Named("web_client_id")
    fun provideWebClientId(): String =
        "884005998722-gvf93pl6v7p3bv5lm44pphg4t2tkk3nm.apps.googleusercontent.com"

    @Provides
    @Singleton
    fun provideGoogleSignInClient(
        @ApplicationContext context: Context,
        @Named("web_client_id") webClientId: String
    ): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(webClientId)
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    // Remove the puzzle database and puzzleDao providers here to avoid duplication.
    // They are already provided in DatabaseModule.kt

    // Provide FirebaseRepository
    @Provides
    @Singleton
    fun provideFirebaseRepository(
        auth: com.google.firebase.auth.FirebaseAuth,
        firestore: com.google.firebase.firestore.FirebaseFirestore
    ): FirebaseRepository {
        return FirebaseRepository(auth, firestore)
    }

    // Provide puzzle generators
    @Provides @Singleton fun provideAlgebraProblemGenerator(): AlgebraProblemGenerator = AlgebraProblemGenerator()
    @Provides @Singleton fun provideAnagramGenerator(): AnagramGenerator = AnagramGenerator(loadDictionary())
    @Provides @Singleton fun provideBiologyMatchingGenerator(): BiologyMatchingGenerator = BiologyMatchingGenerator()
    @Provides @Singleton fun provideCapitalCityMatchingGenerator(): CapitalCityMatchingGenerator = CapitalCityMatchingGenerator()
    @Provides @Singleton fun provideChemistryEquationBalancer(): ChemistryEquationBalancer = ChemistryEquationBalancer()
    @Provides @Singleton fun provideCrosswordGenerator(): CrosswordGenerator = CrosswordGenerator()
    @Provides @Singleton fun provideEnvironmentalScenarioGenerator(): EnvironmentalScenarioGenerator = EnvironmentalScenarioGenerator()
    @Provides @Singleton fun provideFlagIdentificationGenerator(): FlagIdentificationGenerator = FlagIdentificationGenerator()
    @Provides @Singleton fun provideGeometryProblemGenerator(): GeometryProblemGenerator = GeometryProblemGenerator()
    @Provides @Singleton fun provideLogicGridGenerator(): LogicGridGenerator = LogicGridGenerator()
    @Provides @Singleton fun providePatternRecognitionGenerator(): PatternRecognitionGenerator = PatternRecognitionGenerator()
    @Provides @Singleton fun providePhysicsKinematicsProblemGenerator(): PhysicsKinematicsProblemGenerator = PhysicsKinematicsProblemGenerator()
    @Provides @Singleton fun provideSudokuGenerator(): SudokuGenerator = SudokuGenerator()
    @Provides @Singleton fun provideWordSearchGenerator(): WordSearchGenerator = WordSearchGenerator()

    // Provide PuzzleRepository
    @Provides
    @Singleton
    fun providePuzzleRepository(
        firebaseRepository: FirebaseRepository,
        puzzleDao: PuzzleDao,  // from DatabaseModule
        algebraGenerator: AlgebraProblemGenerator,
        anagramGenerator: AnagramGenerator,
        biologyMatchingGenerator: BiologyMatchingGenerator,
        capitalCityMatchingGenerator: CapitalCityMatchingGenerator,
        chemistryEquationBalancer: ChemistryEquationBalancer,
        crosswordGenerator: CrosswordGenerator,
        environmentalScenarioGenerator: EnvironmentalScenarioGenerator,
        flagIdentificationGenerator: FlagIdentificationGenerator,
        geometryProblemGenerator: GeometryProblemGenerator,
        logicGridGenerator: LogicGridGenerator,
        patternRecognitionGenerator: PatternRecognitionGenerator,
        physicsKinematicsProblemGenerator: PhysicsKinematicsProblemGenerator,
        sudokuGenerator: SudokuGenerator,
        wordSearchGenerator: WordSearchGenerator
    ): PuzzleRepository {
        return PuzzleRepository(
            firebaseRepository,
            puzzleDao,
            algebraGenerator,
            anagramGenerator,
            biologyMatchingGenerator,
            capitalCityMatchingGenerator,
            chemistryEquationBalancer,
            crosswordGenerator,
            environmentalScenarioGenerator,
            flagIdentificationGenerator,
            geometryProblemGenerator,
            logicGridGenerator,
            patternRecognitionGenerator,
            physicsKinematicsProblemGenerator,
            sudokuGenerator,
            wordSearchGenerator
        )
    }

    /**
     * Loads the dictionary from assets or a predefined set.
     * Modify as needed to load from a real file if desired.
     */
    private fun loadDictionary(): Set<String> {
        // Placeholder example
        return setOf("Kotlin", "Android", "Firebase", "Compose", "Hilt", "Room")
    }
}
