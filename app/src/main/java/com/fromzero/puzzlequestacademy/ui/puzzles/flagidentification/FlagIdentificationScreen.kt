// ui/puzzles/flagidentification/FlagIdentificationScreen.kt

package com.fromzero.puzzlequestacademy.ui.puzzles.flagidentification

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.fromzero.puzzlequestacademy.R
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FlagIdentificationScreen(
    navController: NavController,
    flagViewModel: FlagIdentificationViewModel = viewModel()
) {
    val puzzle by flagViewModel.puzzle.observeAsState()
    val feedback by flagViewModel.feedback.observeAsState()
    val coroutineScope = rememberCoroutineScope()

    var userAnswer by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Flag Identification") },
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
                FlagIdentificationContent(
                    puzzle = puzzle!!,
                    userAnswer = userAnswer,
                    onAnswerChange = { userAnswer = it },
                    onSubmit = { coroutineScope.launch { flagViewModel.submitAnswer(userAnswer) } },
                    feedback = feedback,
                    onUseHint = { flagViewModel.useHint() },
                    onClearFeedback = { flagViewModel.clearFeedback() }
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
fun FlagIdentificationContent(
    puzzle: com.fromzero.puzzlequestacademy.data.model.FlagIdentificationPuzzle,
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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Identify the country by its flag:",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Assuming flagImagePath corresponds to a drawable resource
        Image(
            painter = painterResource(id = getDrawableResource(puzzle.flagImagePath)),
            contentDescription = "Flag Image",
            modifier = Modifier
                .size(200.dp)
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = userAnswer,
            onValueChange = onAnswerChange,
            label = { Text("Country Name") },
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
                Text("Use Hint (-5 Hints)")
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

@Composable
fun getDrawableResource(flagImagePath: String): Int {
    // Map flagImagePath to actual drawable resource IDs
    // Implement the mapping based on your resources
    // Example:
    return when (flagImagePath.lowercase()) {
        "flags/canada.png" -> R.drawable.canada_flag
        "flags/brazil.png" -> R.drawable.brazil_flag
        "flags/germany.png" -> R.drawable.germany_flag
        "flags/australia.png" -> R.drawable.australia_flag
        "flags/japan.png" -> R.drawable.japan_flag
        else -> R.drawable.ic_flag_placeholder
    }
}
