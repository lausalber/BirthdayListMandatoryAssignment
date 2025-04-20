package com.example.birthdaylistmandatoryassignment.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseUser

@Composable
fun AuthenticationScreen(
    user: FirebaseUser? = null,
    message: String = "",
    signIn: (email: String, password: String) -> Unit,
    register: (email: String, password: String) -> Unit,
    navigateToBirthdayList: () -> Unit
) {
    if (user != null) {
        LaunchedEffect(Unit) {
            navigateToBirthdayList()
        }
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailIsError by remember { mutableStateOf(false) }
    var passwordIsError by remember { mutableStateOf(false) }
    var showPassword by remember { mutableStateOf(false) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(
                    text = "Log In",
                    fontSize = 40.sp
                )
            }

            // Email Field + Error
            Row {
                OutlinedTextField(
                    modifier = Modifier.padding(6.dp),
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    isError = emailIsError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                )
            }
            if (emailIsError) {
                when {
                    email.isEmpty() -> Text(
                        "Email cannot be empty",
                        color = MaterialTheme.colorScheme.error
                    )

                    !validateEmail(email) -> Text(
                        "Invalid email format",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            // Password Field + Error
            Row {
                OutlinedTextField(
                    modifier = Modifier.padding(6.dp),
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation =
                        if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    isError = passwordIsError,
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
            }
            if (passwordIsError) {
                Text("Password cannot be empty", color = MaterialTheme.colorScheme.error)
            }



            // Register Button
            Row {
                Button(onClick = {
                    email = email.trim()
                    password = password.trim()

                    emailIsError = email.isEmpty() || !validateEmail(email)
                    passwordIsError = password.isEmpty()

                    if (!emailIsError && !passwordIsError) {
                        register(email, password)
                    }
                }) {
                    Text("Register")
                }
            }

            // Sign-in Button
            Row {
                Button(onClick = {
                    email = email.trim()
                    password = password.trim()

                    emailIsError = email.isEmpty() || !validateEmail(email)
                    passwordIsError = password.isEmpty()

                    if (!emailIsError && !passwordIsError) {
                        signIn(email, password)
                    }
                }) {
                    Text("Sign in")
                }
            }
        }
    }
}

private fun validateEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Preview(showBackground = true)
@Composable
fun AuthenticationScreenPreview() {
    AuthenticationScreen(
        user = null,
        message = "",
        signIn = { _, _ -> },
        register = { _, _ -> },
        navigateToBirthdayList = {}
    )

}