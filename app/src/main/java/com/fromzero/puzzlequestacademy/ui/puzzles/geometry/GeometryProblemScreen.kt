// ui/puzzles/geometry/GeometryProblemScreen.kt

package com.fromzero.puzzlequestacademy.ui.puzzles.geometry

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
fun GeometryProblemScreen(
    navController: NavController,
    geometryViewModel: GeometryViewModel = viewModel()
) {
    val problem by geometryViewModel.problem.observeAsState()
    val feedback by geometryViewModel.feedback.observeAsState()
    val coroutineScope = rememberCoroutineScope()

    var userAnswer by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Geometry Problem") },
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
                GeometryProblemContent(
                    problem = problem!!,
                    userAnswer = userAnswer,
                    onAnswerChange = { userAnswer = it },
                    onSubmit = {
                        val answerDouble = userAnswer.toDoubleOrNull()
                        if (answerDouble != null) {
                            coroutineScope.launch { geometryViewModel.submitAnswer(answerDouble) }
                        } else {
                            coroutineScope.launch {  geometryViewModel.submitAnswer(-1.0) } // Invalid answer
                        }
                    },
                    feedback = feedback,
                    onUseHint = { geometryViewModel.useHint() },
                    onClearFeedback = { geometryViewModel.clearFeedback() }
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
fun GeometryProblemContent(
    problem: com.fromzero.puzzlequestacademy.data.model.GeometryProblem,
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
            text = "Solve the following geometry problem:",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = problem.question,
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
