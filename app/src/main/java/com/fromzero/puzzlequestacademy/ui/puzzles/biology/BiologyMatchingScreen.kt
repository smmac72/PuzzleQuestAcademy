// ui/puzzles/biology/BiologyMatchingScreen.kt

package com.fromzero.puzzlequestacademy.ui.puzzles.biology

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BiologyMatchingScreen(
    navController: NavController,
    biologyViewModel: BiologyMatchingViewModel = hiltViewModel()
) {
    val puzzle by biologyViewModel.puzzle.observeAsState()
    val feedback by biologyViewModel.feedback.observeAsState()
    val coroutineScope = rememberCoroutineScope()

    var userMatches by remember { mutableStateOf<List<String>>(emptyList()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Biology Matching") },
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
                BiologyMatchingContent(
                    puzzle = puzzle!!,
                    userMatches = userMatches,
                    onMatchChange = { index, value ->
                        userMatches = userMatches.toMutableList().also { it[index] = value }
                    },
                    onSubmit = { coroutineScope.launch { biologyViewModel.submitMatches(userMatches) } },
                    feedback = feedback,
                    onUseHint = { biologyViewModel.useHint() },
                    onClearFeedback = { biologyViewModel.clearFeedback() }
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
fun BiologyMatchingContent(
    puzzle: com.fromzero.puzzlequestacademy.data.model.BiologyMatchingPuzzle,
    userMatches: List<String>,
    onMatchChange: (Int, String) -> Unit,
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
            text = "Match the following items:",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(16.dp))
        for ((index, item) in puzzle.items.withIndex()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "- $item")
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = userMatches.getOrNull(index) ?: "",
                    onValueChange = { onMatchChange(index, it) },
                    label = { Text("Match") },
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
                Text("Use Hint (-10 Hints)")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        feedback?.let {
            Text(
                text = it,
                color = if (it.contains("All matches are correct")) MaterialTheme.colors.primary else MaterialTheme.colors.error,
                style = MaterialTheme.typography.body1
            )
        }
    }
}
