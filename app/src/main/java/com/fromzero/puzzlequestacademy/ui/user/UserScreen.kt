// UserProfileScreen.kt

package com.fromzero.puzzlequestacademy.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fromzero.puzzlequestacademy.data.model.UserProfile
import com.fromzero.puzzlequestacademy.ui.user.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun UserProfileScreen(userViewModel: UserViewModel = hiltViewModel()) {
    var userId by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var fetchedProfile by remember { mutableStateOf<UserProfile?>(null) }

    // Obtain a CoroutineScope tied to the composable
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "User Profile", style = MaterialTheme.typography.h4)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = userId,
            onValueChange = { userId = it },
            label = { Text("User ID") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val userProfile = UserProfile(
                    userId = userId,
                    username = name,
                    email = email,
                    hints = 5
                )
                userViewModel.addUserProfile(userProfile)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Save Profile")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    fetchedProfile = userViewModel.fetchUserProfile(userId)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Fetch Profile")
        }

        Spacer(modifier = Modifier.height(16.dp))

        fetchedProfile?.let { profile ->
            Text(text = "Fetched Profile:", style = MaterialTheme.typography.h6)
            Text(text = "Name: ${profile.username}")
            Text(text = "Email: ${profile.email}")
        }
    }
}
