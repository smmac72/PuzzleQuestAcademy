// MainActivity.kt
package com.fromzero.puzzlequestacademy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.fromzero.puzzlequestacademy.main.PuzzleQuestAcademyApp
import com.fromzero.puzzlequestacademy.navigation.PuzzleNavHost
import com.fromzero.puzzlequestacademy.ui.auth.AuthScreen
import com.fromzero.puzzlequestacademy.ui.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint
import com.fromzero.puzzlequestacademy.ui.user.UserScreen
import com.fromzero.puzzlequestacademy.ui.theme.PuzzleQuestAcademyTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PuzzleQuestAcademyTheme {
                val navController = rememberNavController()
                PuzzleNavHost(navController = navController)
            }
        }
    }
}
