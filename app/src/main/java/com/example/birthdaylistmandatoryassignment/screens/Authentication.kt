package com.example.birthdaylistmandatoryassignment.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalConfiguration
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

    val configuration = LocalConfiguration.current

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                VerticalAuthentication(
                    email = email,
                    onEmailChange = { email = it },
                    password = password,
                    onPasswordChange = { password = it },
                    emailIsError = emailIsError,
                    passwordIsError = passwordIsError,
                    showPassword = showPassword,
                    onTogglePasswordVisibility = { showPassword = !showPassword },
                    message = message,
                    signIn = { e, p ->
                        email = e.trim()
                        password = p.trim()
                        emailIsError = email.isEmpty() || !validateEmail(email)
                        passwordIsError = password.isEmpty()
                        if (!emailIsError && !passwordIsError) {
                            signIn(email, password)
                        }
                    },
                    register = { e, p ->
                        email = e.trim()
                        password = p.trim()
                        emailIsError = email.isEmpty() || !validateEmail(email)
                        passwordIsError = password.isEmpty()
                        if (!emailIsError && !passwordIsError) {
                            register(email, password)
                        }
                    }
                )
            } else {
                HorizontalAuthentication(
                    email = email,
                    onEmailChange = { email = it },
                    password = password,
                    onPasswordChange = { password = it },
                    emailIsError = emailIsError,
                    passwordIsError = passwordIsError,
                    showPassword = showPassword,
                    onTogglePasswordVisibility = { showPassword = !showPassword },
                    message = message,
                    signIn = { e, p ->
                        email = e.trim()
                        password = p.trim()
                        emailIsError = email.isEmpty() || !validateEmail(email)
                        passwordIsError = password.isEmpty()
                        if (!emailIsError && !passwordIsError) {
                            signIn(email, password)
                        }
                    },
                    register = { e, p ->
                        email = e.trim()
                        password = p.trim()
                        emailIsError = email.isEmpty() || !validateEmail(email)
                        passwordIsError = password.isEmpty()
                        if (!emailIsError && !passwordIsError) {
                            register(email, password)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun VerticalAuthentication(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    emailIsError: Boolean,
    passwordIsError: Boolean,
    showPassword: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    message: String,
    signIn: (email: String, password: String) -> Unit,
    register: (email: String, password: String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Log In", fontSize = 40.sp)

        OutlinedTextField(
            modifier = Modifier.padding(6.dp),
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email") },
            isError = emailIsError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )

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

        OutlinedTextField(
            modifier = Modifier.padding(6.dp),
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation =
                if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            isError = passwordIsError,
            trailingIcon = {
                IconButton(onClick = onTogglePasswordVisibility) {
                    if (showPassword) {
                        Icon(Icons.Filled.Visibility, contentDescription = "Hide password")
                    } else {
                        Icon(Icons.Filled.VisibilityOff, contentDescription = "Show password")
                    }
                }
            }
        )

        if (passwordIsError) {
            Text("Password cannot be empty", color = MaterialTheme.colorScheme.error)
        }

        if (message.isNotEmpty()) {
            if (message != "User is null") {
                Text(message, color = MaterialTheme.colorScheme.error)
            }
        }

        Row(
            modifier = Modifier.padding(top = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(onClick = { register(email, password) }) {
                Text("Register")
            }
            Button(onClick = { signIn(email, password) }) {
                Text("Sign in")
            }
        }
    }
}


@Composable
fun HorizontalAuthentication(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    emailIsError: Boolean,
    passwordIsError: Boolean,
    showPassword: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    message: String,
    signIn: (email: String, password: String) -> Unit,
    register: (email: String, password: String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Log In", fontSize = 40.sp)

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    OutlinedTextField(
                        value = email,
                        onValueChange = onEmailChange,
                        label = { Text("Email") },
                        isError = emailIsError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.width(250.dp)
                    )
                    if (emailIsError) {
                        Text(
                            text = if (email.isEmpty()) "Email cannot be empty" else "Invalid email format",
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp
                        )
                    }
                }

                Column {
                    OutlinedTextField(
                        value = password,
                        onValueChange = onPasswordChange,
                        label = { Text("Password") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation =
                            if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        isError = passwordIsError,
                        trailingIcon = {
                            IconButton(onClick = onTogglePasswordVisibility) {
                                Icon(
                                    imageVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                    contentDescription = if (showPassword) "Hide password" else "Show password"
                                )
                            }
                        },
                        modifier = Modifier.width(250.dp)
                    )
                    if (passwordIsError) {
                        Text(
                            "Password cannot be empty",
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            if (message.isNotEmpty()) {
                if (message != "User is null") {
                    Text(message, color = MaterialTheme.colorScheme.error)
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(onClick = { register(email, password) }) {
                    Text("Register")
                }

                Button(onClick = { signIn(email, password) }) {
                    Text("Sign in")
                }
            }
        }
    }
}

private fun validateEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Composable
@Preview(showBackground = true)
fun VerticalAuthenticationScreen() {
    VerticalAuthentication(
        email = "",
        onEmailChange = {},
        password = "",
        onPasswordChange = {},
        emailIsError = false,
        passwordIsError = false,
        showPassword = false,
        onTogglePasswordVisibility = {},
        message = "",
        signIn = { _, _ -> },
        register = { _, _ -> })
}


@Composable
@Preview(
    showBackground = true,
    widthDp = 800,
    heightDp = 350
)
fun HorizontalAuthenticationScreen() {
    HorizontalAuthentication(
        email = "",
        onEmailChange = {},
        password = "",
        onPasswordChange = {},
        emailIsError = false,
        passwordIsError = false,
        showPassword = false,
        onTogglePasswordVisibility = {},
        message = "",
        signIn = { _, _ -> },
        register = { _, _ -> }
    )
}