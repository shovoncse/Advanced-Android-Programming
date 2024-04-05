package com.example.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class LoginData(val username: String, val password: String)

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onLoginClicked: (String, String) -> Unit,
    onSignUpClicked: () -> Unit,
    onLoginSuccess: () -> Unit,
    initialEmail: String
) {
    val loginStatusMessage by authViewModel.loginStatusMessage.collectAsState(initial = "")
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

    val loginData = remember { mutableStateOf(LoginData(initialEmail, "")) }
    var loginErrorMessage by remember { mutableStateOf("") }

    LaunchedEffect(loginStatusMessage) {
        if (loginStatusMessage.isNotEmpty()) {
            loginErrorMessage = loginStatusMessage
        }
    }

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = loginData.value.username,
            onValueChange = { newUsername -> loginData.value = loginData.value.copy(username = newUsername) },
            label = { Text("Username") },
            singleLine = true,
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email Icon") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = loginData.value.password,
            onValueChange = { newPassword -> loginData.value = loginData.value.copy(password = newPassword) },
            label = { Text("Password") },
            singleLine = true,
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Lock Icon") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Button(
            onClick = {
                // Call the login function from AuthViewModel
                onLoginClicked(loginData.value.username, loginData.value.password)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Submit", color = MaterialTheme.colorScheme.onPrimary)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Don't have an account? Sign up",
            modifier = Modifier.clickable { onSignUpClicked() },
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp
        )
    }
}
