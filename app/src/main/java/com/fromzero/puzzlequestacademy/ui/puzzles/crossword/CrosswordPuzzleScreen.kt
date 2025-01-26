// ui/puzzles/crossword/CrosswordPuzzleScreen.kt

package com.fromzero.puzzlequestacademy.ui.puzzles.crossword

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.*
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.fromzero.puzzlequestacademy.ui.puzzles.crossword.CrosswordViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CrosswordPuzzleScreen(
    navController: NavController,
    crosswordViewModel: CrosswordViewModel = viewModel()
) {
    val puzzle by crosswordViewModel.puzzle.observeAsState()
    val feedback by crosswordViewModel.feedback.observeAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crossword Puzzle") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = {
            if (puzzle != null) {
                CrosswordPuzzleContent(
                    puzzle = puzzle!!,
                    onSubmit = { coroutineScope.launch { crosswordViewModel.submitPuzzle() } },
                    feedback = feedback,
                    onUseHint = { crosswordViewModel.useHint() },
                    onClearFeedback = { crosswordViewModel.clearFeedback() }
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
fun CrosswordPuzzleContent(
    puzzle: com.fromzero.puzzlequestacademy.data.model.CrosswordPuzzle,
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
            text = "Complete the crossword based on the clues below.",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Placeholder for actual crossword grid UI
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.LightGray)
                .border(2.dp, Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text("Crossword Grid Placeholder")
        }
        Spacer(modifier = Modifier.height(16.dp))
        // List of clues
        Text(text = "Clues:", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))
        for ((index, clue) in puzzle.clues.withIndex()) {
            Text(text = "${index + 1}. $clue")
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
                Text("Use Hint (-10 Hints)")
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
