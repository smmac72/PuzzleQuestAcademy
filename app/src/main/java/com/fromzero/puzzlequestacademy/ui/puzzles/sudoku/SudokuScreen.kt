// ui/puzzles/sudoku/SudokuScreen.kt

package com.fromzero.puzzlequestacademy.ui.puzzles.sudoku

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun SudokuScreen(
    navController: NavController,
    sudokuViewModel: SudokuViewModel = hiltViewModel()
) {
    val puzzle by sudokuViewModel.puzzle.observeAsState()
    val feedback by sudokuViewModel.feedback.observeAsState()
    val coroutineScope = rememberCoroutineScope()

    //var submittedGrid by remember { mutableStateOf<MutableList<MutableList<Int?>>>(Array(9) { MutableList(9) { null } } }
    /*
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sudoku Puzzle") },
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
                SudokuPuzzleContent(
                    puzzle = puzzle!!,
                    submittedGrid = submittedGrid,
                    onCellInput = { row, col, value ->
                        submittedGrid = submittedGrid.deepCopy().also {
                            it.grid[row][col] = value
                        }
                    },
                    onSubmit = { coroutineScope.launch { sudokuViewModel.submitAnswer(submittedGrid) } },
                    feedback = feedback,
                    onUseHint = { sudokuViewModel.useHint() },
                    onClearFeedback = { sudokuViewModel.clearFeedback() }
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
    )*/
}

@Composable
fun SudokuPuzzleContent(
    puzzle: com.fromzero.puzzlequestacademy.data.model.SudokuPuzzle,
    submittedGrid: Array<Array<Int?>>,
    onCellInput: (Int, Int, Int?) -> Unit,
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
            text = "Fill in the blanks to complete the Sudoku.",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(16.dp))
        SudokuGrid(
            puzzleGrid = puzzle.grid,
            submittedGrid = submittedGrid,
            onCellInput = onCellInput
        )
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

@Composable
fun SudokuGrid(
    puzzleGrid: MutableList<MutableList<Int?>>,
    submittedGrid: Array<Array<Int?>>,
    onCellInput: (Int, Int, Int?) -> Unit
) {
    Column(
        modifier = Modifier
            .size(300.dp)
            .border(2.dp, Color.Black)
    ) {
        for (i in 0 until 9) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                for (j in 0 until 9) {
                    val cellValue = if (puzzleGrid[i][j] != null) {
                        puzzleGrid[i][j]
                    } else {
                        submittedGrid[i][j]
                    }
                    val isEditable = puzzleGrid[i][j] == null
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .border(BorderStroke(0.5.dp, Color.Gray))
                            .background(
                                if ((i / 3 + j / 3) % 2 == 0) Color(0xFFE0E0E0) else Color.White
                            )
                    ) {
                        if (isEditable) {
                            var text by remember { mutableStateOf(cellValue?.toString() ?: "") }
                            BasicTextField(
                                value = text,
                                onValueChange = {
                                    if (it.length <= 1 && it.all { char -> char.isDigit() }) {
                                        text = it
                                        val num = it.toIntOrNull()
                                        onCellInput(i, j, num)
                                    }
                                },
                                singleLine = true,
                                textStyle = TextStyle(color = Color.Black),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(4.dp)
                            )
                        } else {
                            Text(
                                text = cellValue?.toString() ?: "",
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }
            }
        }
    }
}
