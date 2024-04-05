package com.example.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import java.nio.file.WatchEvent

@Composable
fun HomeScreen(
    authViewModel: AuthViewModel,
    onLogout: () -> Unit // Added onLogout parameter
) {
    val userEmail = authViewModel.userEmail.collectAsState().value ?: "Unknown User"
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display a welcome message including the user's email
        Text(
            text = "Welcome, $userEmail",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(onClick = onLogout) {
            Text("Logout")
        }
    }
}
