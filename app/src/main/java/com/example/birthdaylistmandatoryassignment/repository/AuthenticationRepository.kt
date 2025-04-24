package com.example.birthdaylistmandatoryassignment.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthenticationRepository {
    private val auth = FirebaseAuth.getInstance()
    private val _user = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    private val _message = MutableStateFlow("")
    val user: StateFlow<FirebaseUser?> = _user.asStateFlow()
    val message: StateFlow<String> = _message.asStateFlow()

    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _user.value = auth.currentUser
                _message.value = ""
                Log.d("APPLE", "signIn completed. User updated to: ${_user.value}")
            } else {
                _user.value = null
                _message.value = task.exception?.message ?: "Unknown error"
                Log.e("APPLE", "signIn failed. Error: ${task.exception?.message}")
            }

        }
    }

    fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _user.value = auth.currentUser
                _message.value = ""
                Log.d("APPLE", "Register completed New user is: ${_user.value}")
            } else {
                _user.value = null
                _message.value = task.exception?.message ?: "Unknown error"
                Log.e("APPLE", "Register failed. Error: ${task.exception?.message}")
            }

        }
    }

    fun signOut() {
        auth.signOut()
        _user.value = null
        Log.d("APPLE", "signOut completed. User is updated to null")
    }
}