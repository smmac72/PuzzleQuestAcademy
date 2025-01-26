// ui/auth/AuthScreen.kt

package com.fromzero.puzzlequestacademy.ui.auth

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fromzero.puzzlequestacademy.navigation.Screen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fromzero.puzzlequestacademy.data.repository.PuzzleRepository
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun AuthScreen(navController: NavController, authViewModel: AuthViewModel = hiltViewModel()) {
    val isLoading by authViewModel.isLoading.collectAsState()
    val errorMessage by authViewModel.errorMessage.collectAsState()
    val userAuthenticated by authViewModel.userAuthenticated.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        authViewModel.onSignInResult(result)
    }

    LaunchedEffect(userAuthenticated) {
        if (userAuthenticated) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Auth.route) { inclusive = true }
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Welcome to PuzzleQuest Academy", style = MaterialTheme.typography.h5)
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = { launcher.launch(authViewModel.getSignInIntent()) }) {
                    Text("Sign in with Google")
                }
                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = errorMessage!!, color = MaterialTheme.colors.error)
                }
            }
        }
    }
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: PuzzleRepository,
    private val googleSignInClient: GoogleSignInClient
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _userAuthenticated = MutableStateFlow(false)
    val userAuthenticated = _userAuthenticated.asStateFlow()

    // Configure Google Sign-In
    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("<YOUR_WEB_CLIENT_ID>") // Replace with your Web Client ID
        .requestEmail()
        .build()

    fun getSignInIntent() = googleSignInClient.signInIntent

    fun onSignInResult(result: androidx.activity.result.ActivityResult) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(Exception::class.java) ?: throw Exception("No account found")
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: Exception) {
            _errorMessage.value = "Sign-In Failed: ${e.message}"
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                val authResult = repository.firebaseRepository.auth.signInWithCredential(credential).await()
                if (authResult.user != null) {
                    _userAuthenticated.value = true
                } else {
                    _errorMessage.value = "Authentication failed."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Authentication Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
