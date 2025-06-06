package com.example.birthdaylistmandatoryassignment.model

import androidx.lifecycle.ViewModel
import com.example.birthdaylistmandatoryassignment.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.StateFlow

class AuthenticationViewModel : ViewModel() {
    private val repository = AuthenticationRepository()

    val user: StateFlow<FirebaseUser?> = repository.user
    val message: StateFlow<String> = repository.message

    fun signIn(email: String, password: String) {
        repository.signIn(email, password)
    }

    fun signOut() {
        repository.signOut()
    }

    fun register(email: String, password: String) {
        repository.register(email, password)
    }
}