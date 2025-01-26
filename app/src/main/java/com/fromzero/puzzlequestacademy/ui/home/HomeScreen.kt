// ui/home/HomeScreen.kt

package com.fromzero.puzzlequestacademy.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fromzero.puzzlequestacademy.R

@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel = hiltViewModel()) {
    val userProfile by homeViewModel.userProfile.collectAsState()
    val hints by homeViewModel.hints.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PuzzleQuest Academy") },
                actions = {
                    IconButton(onClick = { homeViewModel.signOut() }) {
                        Icon(painter = painterResource(id = R.drawable.ic_logout), contentDescription = "Logout")
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // User Profile
                userProfile?.let {
                    Text(text = "Hello, ${it.username}!", style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Hints: $hints", style = MaterialTheme.typography.body1)
                }
                Spacer(modifier = Modifier.height(24.dp))

                // Puzzle Grid
                Text(text = "Select a Puzzle", style = MaterialTheme.typography.h5)
                Spacer(modifier = Modifier.height(16.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(homeViewModel.puzzles) { puzzle ->
                        PuzzleCard(puzzle = puzzle) {
                            navController.navigate(puzzle.route)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun PuzzleCard(puzzle: HomeViewModel.Puzzle, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() },
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = puzzle.iconRes),
                contentDescription = puzzle.name,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = puzzle.name, style = MaterialTheme.typography.subtitle1)
        }
    }
}

// Sample Puzzle Data Class
data class Puzzle(
    val name: String,
    val route: String,
    val iconRes: Int
)
