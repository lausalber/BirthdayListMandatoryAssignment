package com.example.birthdaylistmandatoryassignment.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.birthdaylistmandatoryassignment.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class AuthenticationViewModel : ViewModel() {
    private val repository = AuthenticationRepository()
    var user: FirebaseUser? by mutableStateOf(repository.user)
    var message by mutableStateOf("")

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            repository.signIn(email, password)
        }
    }

    fun signOut() {
        viewModelScope.launch {
            repository.signOut()
        }
    }

    fun register(email: String, password: String) {
            repository.register(email, password)
        }
}

