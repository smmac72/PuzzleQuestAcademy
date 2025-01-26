// ui/puzzles/logicgrid/LogicGridScreen.kt

package com.fromzero.puzzlequestacademy.ui.puzzles.logicgrid

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LogicGridScreen(
    navController: NavController,
    logicGridViewModel: LogicGridViewModel = viewModel()
) {
    val puzzle by logicGridViewModel.puzzle.observeAsState()
    val feedback by logicGridViewModel.feedback.observeAsState()
    val coroutineScope = rememberCoroutineScope()

    var userAnswers by remember { mutableStateOf<List<String>>(emptyList()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Logic Grid Puzzle") },
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
                LogicGridPuzzleContent(
                    puzzle = puzzle!!,
                    userAnswers = userAnswers,
                    onAnswerChange = { index, value ->
                        userAnswers = userAnswers.toMutableList().also { it[index] = value }
                    },
                    onSubmit = { coroutineScope.launch { logicGridViewModel.submitAnswers(userAnswers) } },
                    feedback = feedback,
                    onUseHint = { logicGridViewModel.useHint() },
                    onClearFeedback = { logicGridViewModel.clearFeedback() }
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
fun LogicGridPuzzleContent(
    puzzle: com.fromzero.puzzlequestacademy.data.model.LogicGridPuzzle,
    userAnswers: List<String>,
    onAnswerChange: (Int, String) -> Unit,
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
            text = "Clues:",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(8.dp))
        for (clue in puzzle.clues) {
            Text(text = "- $clue")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Questions:",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(8.dp))
        for ((index, question) in puzzle.questions.withIndex()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "${index + 1}. $question")
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = userAnswers.getOrNull(index) ?: "",
                    onValueChange = { onAnswerChange(index, it) },
                    label = { Text("Answer") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
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
                Text("Use Hint (-15 Hints)")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        feedback?.let {
            Text(
                text = it,
                color = if (it.contains("Correct")) MaterialTheme.colors.primary else MaterialTheme.colors.error,
                style = MaterialTheme.typography.body1
            )
        }
    }
}
