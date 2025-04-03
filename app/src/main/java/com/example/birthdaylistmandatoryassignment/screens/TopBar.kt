package com.example.birthdaylistmandatoryassignment.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.firebase.auth.FirebaseUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    user : String?,
    logOut : () -> Unit,
    onNavigateToLogIn: () -> Unit){
    TopAppBar(
        title = {
            Text("My Birthdays")
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        actions = {
            if (user != null) {
                IconButton(onClick = {
                    logOut()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Log out"
                    )
                }
            }
        }
    )
    LaunchedEffect(user) {
        if (user != null) {
            onNavigateToLogIn()
        }
    }
}