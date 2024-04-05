package com.example.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val _isLoggedIn = MutableStateFlow(auth.currentUser != null)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _userEmail = MutableStateFlow(auth.currentUser?.email ?: "")
    val userEmail: StateFlow<String> = _userEmail

    private val _showLogin = MutableStateFlow(true)
    val showLogin: StateFlow<Boolean> = _showLogin

    private val _loginStatusMessage = Channel<String>()
    val loginStatusMessage = _loginStatusMessage.receiveAsFlow()

    init {
        auth.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            _isLoggedIn.value = user != null
            _userEmail.value = user?.email ?: ""
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                _isLoggedIn.value = true
                _loginStatusMessage.send("Login successful")
            } catch (e: Exception) {
                _isLoggedIn.value = false
                _loginStatusMessage.send(e.message ?: "An error occurred during login")
            }
        }
    }

    fun logout() {
        auth.signOut()
        _isLoggedIn.value = false
        _userEmail.value = "" // Clear user email on logout
    }

    fun signup(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                _isLoggedIn.value = true
                _loginStatusMessage.send("Signup successful")
                onSuccess()
            } catch (e: Exception) {
                _loginStatusMessage.send(e.message ?: "An error occurred during signup")
                onError(e.message ?: "An error occurred during signup")
            }
        }
    }
    fun showLoginScreen(show: Boolean) {
        _showLogin.value = show

}
}
