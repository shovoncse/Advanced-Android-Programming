package com.example.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

fun createUserWithEmailAndPassword(
    email: String,
    password: String,
    onSuccess: (String) -> Unit,
    onError: (String) -> Unit
) {
    // Simulated Firebase call - replace this with real Firebase authentication
    if (email.isNotEmpty() && password.length >= 6) { // Simple validation for example
        onSuccess(email) // Simulate successful signup
    } else {
        onError("Failed to create user") // Simulate error
    }
}

@Composable
fun SignupScreen(
    authViewModel: AuthViewModel,
    onLoginClicked: () -> Unit,
    onSignupSuccess: Any
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            ),
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            ),
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        Button(
            onClick = {
                if (password == confirmPassword) {
                    // Call the signup function using the ViewModel
                    authViewModel.signup(email, password, {
                        // On success, invoke the onSignupSuccess callback
                        // Do something on success if needed
                    }) { errMsg ->
                        errorMessage = errMsg
                    }
                } else {
                    errorMessage = "Passwords do not match"
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Sign Up")
        }
        // Button to navigate to the login screen
        Button(
            onClick = onLoginClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Already have an account? Log in")
        }
    }
}
