// ui/puzzles/wordsearch/WordSearchScreen.kt

package com.fromzero.puzzlequestacademy.ui.puzzles.wordsearch

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.fromzero.puzzlequestacademy.data.model.WordSearchPuzzle
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WordSearchScreen(
    navController: NavController,
    wordSearchViewModel: WordSearchViewModel = hiltViewModel()
) {
    val puzzle by wordSearchViewModel.puzzle.observeAsState()
    val feedback by wordSearchViewModel.feedback.observeAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Word Search Puzzle") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = {
            if (puzzle != null) {
                WordSearchPuzzleContent(
                    puzzle = puzzle!!,
                    onSubmit = { coroutineScope.launch { wordSearchViewModel.submitPuzzle() }  },
                    feedback = feedback,
                    onUseHint = { wordSearchViewModel.useHint() },
                    onClearFeedback = { wordSearchViewModel.clearFeedback() }
                )
            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    )
}

@Composable
fun WordSearchPuzzleContent(
    puzzle: WordSearchPuzzle,
    onSubmit: () -> Unit,
    feedback: String?,
    onUseHint: () -> Unit,
    onClearFeedback: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Find the listed words in the grid.",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Placeholder for actual word search grid UI
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .border(2.dp, Color.Black)
        ) {
            // Implement drawing of the word search grid
            drawRect(color = Color.LightGray)
            // Add grid lines and words as needed
        }
        Spacer(modifier = Modifier.height(16.dp))
        // List of words to find
        Text(text = "Words to Find:", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))
        for (word in puzzle.words) {
            Text(text = "- $word")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = onSubmit,
                modifier = Modifier.weight(1f)
            ) {
                Text("Submit")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = onUseHint,
                modifier = Modifier.weight(1f)
            ) {
                Text("Use Hint (-20 Hints)")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        feedback?.let {
            Text(
                text = it,
                color = if (it.startsWith("C")) MaterialTheme.colors.primary else MaterialTheme.colors.error,
                style = MaterialTheme.typography.body1
            )
        }
    }
}
