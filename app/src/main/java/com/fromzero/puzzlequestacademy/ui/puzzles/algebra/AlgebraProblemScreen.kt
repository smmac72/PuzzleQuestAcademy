// ui/puzzles/algebra/AlgebraProblemScreen.kt

package com.fromzero.puzzlequestacademy.ui.puzzles.algebra

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
fun AlgebraProblemScreen(
    navController: NavController,
    algebraViewModel: AlgebraViewModel = viewModel()
) {
    val problem by algebraViewModel.problem.observeAsState()
    val feedback by algebraViewModel.feedback.observeAsState()
    val coroutineScope = rememberCoroutineScope()

    var userAnswer by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Algebra Problem") },
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
            if (problem != null) {
                AlgebraProblemContent(
                    problem = problem!!,
                    userAnswer = userAnswer,
                    onAnswerChange = { userAnswer = it },
                    onSubmit = {
                        val answerDouble = userAnswer.toDoubleOrNull()
                        if (answerDouble != null) {
                            coroutineScope.launch { algebraViewModel.submitAnswer(answerDouble) }
                        } else {
                            coroutineScope.launch { algebraViewModel.submitAnswer(-1.0) } // Invalid answer
                        }
                    },
                    feedback = feedback,
                    onUseHint = { algebraViewModel.useHint() },
                    onClearFeedback = { algebraViewModel.clearFeedback() }
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
fun AlgebraProblemContent(
    problem: com.fromzero.puzzlequestacademy.data.model.AlgebraProblem,
    userAnswer: String,
    onAnswerChange: (String) -> Unit,
    onSubmit: () -> Unit,
    feedback: String?,
    onUseHint: () -> Unit,
    onClearFeedback: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Solve the following equation:",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = problem.equation,
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = userAnswer,
            onValueChange = onAnswerChange,
            label = { Text("Your Answer") },
            modifier = Modifier.fillMaxWidth()
        )
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
                color = if (it.startsWith("C")) MaterialTheme.colors.primary else MaterialTheme.colors.error,
                style = MaterialTheme.typography.body1
            )
        }
    }
}
