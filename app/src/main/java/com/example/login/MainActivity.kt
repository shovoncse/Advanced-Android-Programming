package com.example.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                AppContent()
            }
        }
    }

    @Composable
    fun MyAppTheme(content: @Composable () -> Unit) {
        MaterialTheme {
            content()
        }
    }

    @Composable
    fun AppContent() {
        val authViewModel: AuthViewModel = viewModel()
        val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

        when {
            isLoggedIn -> HomeScreenWithLogout(authViewModel)
            else -> LoginOrSignupScreen(authViewModel)
        }
    }

    @Composable
    fun HomeScreenWithLogout(authViewModel: AuthViewModel) {
        HomeScreen(
            authViewModel = authViewModel,
            onLogout = { authViewModel.logout() }
        )
    }

    @Composable
    fun LoginOrSignupScreen(authViewModel: AuthViewModel) {
        val showLoginScreen by authViewModel.showLogin.collectAsState()

        if (showLoginScreen) {
            LoginScreenComponent(authViewModel)
        } else {
            SignupScreenComponent(authViewModel)
        }
    }

    @Composable
    fun LoginScreenComponent(authViewModel: AuthViewModel) {
        LoginScreen(
            authViewModel = authViewModel,
            onLoginClicked = { email, password ->
                authViewModel.login(email, password)
            },
            onSignUpClicked = {
                authViewModel.showLoginScreen(false)
            },
            onLoginSuccess = {},
            initialEmail = ""
        )
    }

    @Composable
    fun SignupScreenComponent(authViewModel: AuthViewModel) {
        SignupScreen(
            authViewModel = authViewModel,
            onSignupSuccess = { email: String ->
                authViewModel.showLoginScreen(true)
            },
            onLoginClicked = {
                authViewModel.showLoginScreen(true)
            }
        )
    }
}
