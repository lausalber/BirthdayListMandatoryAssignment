package com.example.birthdaylistmandatoryassignment.repository

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthenticationRepository {

    private val auth = FirebaseAuth.getInstance()

    // Internal mutable state
    private val _user = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    private val _message = MutableStateFlow("")

    // Public immutable state
    val user: StateFlow<FirebaseUser?> = _user.asStateFlow()
    val message: StateFlow<String> = _message.asStateFlow()

    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _user.value = auth.currentUser
                    _message.value = ""
                } else {
                    _user.value = null
                    _message.value = task.exception?.message ?: "Unknown error"
                }
                Log.d("AUTH", "signIn completed. User updated to: ${_user.value}")
            }
    }

    fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _user.value = auth.currentUser
                    _message.value = ""
                } else {
                    _user.value = null
                    _message.value = task.exception?.message ?: "Unknown error"
                }
                Log.d("AUTH", "register completed. User updated to: ${_user.value}")
            }
    }

    fun signOut() {
        auth.signOut()
        _user.value = null
    }
}



