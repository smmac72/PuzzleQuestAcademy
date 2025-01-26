// MainActivity.kt

package com.fromzero.puzzlequestacademy.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import com.fromzero.puzzlequestacademy.ui.UserProfileScreen
import com.fromzero.puzzlequestacademy.ui.theme.PuzzleQuestAcademyTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PuzzleQuestAcademyTheme {
                UserProfileScreen()
            }
        }
    }
}
