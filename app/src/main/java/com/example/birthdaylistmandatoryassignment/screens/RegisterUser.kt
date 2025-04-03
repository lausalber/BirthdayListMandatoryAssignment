package com.example.birthdaylistmandatoryassignment.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lint.kotlin.metadata.Visibility
import com.example.birthdaylistmandatoryassignment.model.AuthenticationViewModel
import com.example.birthdaylistmandatoryassignment.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseUser

@Composable
fun RegisterUser(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onNavigateToLogIn: () -> Unit,
    message: String = "",
    register: (email: String, password: String) -> Unit,
    authViewModel : AuthenticationViewModel,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    var emailIsError by remember { mutableStateOf(false) }
    var passwordIsError by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }

    Scaffold { innerPadding ->
        modifier.padding(innerPadding)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(
                    text = "Register User",
                    fontSize = 40.sp
                )
            }
            Row {
                OutlinedTextField(
                    modifier = modifier.padding(6.dp),
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Username") },
                    isError = emailIsError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                if (emailIsError) {
                    Text("Invalid email", color = MaterialTheme.colorScheme.error)
                }
            }
            Row {
                OutlinedTextField(
                    modifier = modifier.padding(6.dp),
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    isError = passwordIsError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation =
                    if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            if (showPassword) {
                                Icon(Icons.Filled.Visibility, contentDescription = "Hide password")
                            } else {
                                Icon(
                                    Icons.Filled.VisibilityOff,
                                    contentDescription = "Show password"
                                )
                            }
                        }
                    }

                )
                if (passwordIsError) {
                    Text("Invalid password", color = MaterialTheme.colorScheme.error)
                }
                if (message.isNotEmpty()) {
                    Text(message, color = MaterialTheme.colorScheme.error)
                }
            }
            Row {
                Button(
                    onClick = {
                        register(email, password) // Call register, nothing else related to navigation
                    }
                ) {
                    Text(text = "Register")
                }
            }

            }
            Row {
                Button(
                    onClick = { onNavigateBack() }
                ) {
                    Text(text = "Cancel")
                }
            }
            LaunchedEffect(authViewModel.user) {
                if (authViewModel.user != null) {
                    onNavigateToLogIn()
                }
            }
        }
    }


