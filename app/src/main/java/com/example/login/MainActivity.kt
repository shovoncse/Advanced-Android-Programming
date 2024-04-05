package com.example.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val authViewModel: AuthViewModel = viewModel()
                val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

                if (isLoggedIn) {
                    HomeScreen(
                        authViewModel = authViewModel,
                        onLogout = {
                            authViewModel.logout()
                        }
                    )
                } else {
                    val showLoginScreen by authViewModel.showLogin.collectAsState()

                    if (showLoginScreen) {
                        LoginScreen(
                            authViewModel = authViewModel,
                            onLoginClicked = { email: String, password: String ->
                                // Call the login function from AuthViewModel
                                authViewModel.login(email, password)
                            },
                            onSignUpClicked = {
                                // Here, navigate to the signup screen
                                authViewModel.showLoginScreen(false)
                            },
                            onLoginSuccess = {
                                // After successful login, navigate to the home screen
                                // In your implementation, you might navigate to the home screen
                                // using a navigation component or any other navigation library.
                            },
                            initialEmail = "" // Set initial email if needed, empty for now
                        )
                    } else {
                        SignupScreen(
                            authViewModel = authViewModel,
                            onSignupSuccess = { email: String ->
                                // After successful signup, navigate to the login screen
                                authViewModel.showLoginScreen(true)
                            },
                            onLoginClicked = {
                                // Provide a callback for login click
                                authViewModel.showLoginScreen(true)
                            }
                        )
                    }
                }
            }
        }
    }
}
