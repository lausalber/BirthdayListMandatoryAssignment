package com.example.birthdaylistmandatoryassignment.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.birthdaylistmandatoryassignment.model.Friend
import com.google.firebase.auth.FirebaseUser

@Composable
fun BirthdayList(
    modifier: Modifier = Modifier,
    onNavigateToAuthentication: () -> Unit,
    onNavigateToAddFriend: () -> Unit,
    onNavigateToEditFriend: (Friend) -> Unit,
    userID: String?,
    friends: List<Friend>,
    deleteFriend: (Friend) -> Unit,
    sortByName: (Boolean) -> Unit,
    sortByAge: (Boolean) -> Unit,
    sortByBirthday: (Boolean) -> Unit,
    onFilterByName: (String) -> Unit,
    onFilterByAge: (Int) -> Unit)
{
    var sortNameAscending by remember { mutableStateOf(true) }
    var sortAgeAscending by remember { mutableStateOf(true) }
    var sortBirthdayAscending by remember { mutableStateOf(true) }
    var nameFragment by remember { mutableStateOf("") }
    var ageFragment by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = { AddFriendAction { onNavigateToAddFriend() } })
    { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            Row {
                OutlinedTextField(
                    value = nameFragment,
                    onValueChange = { nameFragment = it },
                    label = { Text("Filter by name") },
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = { onFilterByName(nameFragment) },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Filter")
                }
            }
            Row {
                OutlinedTextField(
                    value = ageFragment,
                    onValueChange = { input ->
                        if (input.all { it.isDigit() }) {
                            ageFragment = input
                        }
                    },
                    label = { Text("Filter by age") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
                Button(
                    onClick = {
                        onFilterByAge(ageFragment.toIntOrNull() ?: 0)
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Filter")
                }
            }
            Row {
                TextButton(onClick = {
                    sortByName(sortNameAscending)
                    sortNameAscending = !sortNameAscending
                }) {
                    Text(text = if (sortNameAscending) "nameDown" else "nameUp")
                }
                TextButton(onClick = {
                    sortByAge(sortAgeAscending)
                    sortAgeAscending = !sortAgeAscending
                }) {
                    Text(text = if (sortAgeAscending) "ageDown" else "ageUp")
                }
                TextButton(onClick = {
                    sortByBirthday(sortBirthdayAscending)
                    sortBirthdayAscending = !sortBirthdayAscending
                }) {
                    Text(text = if (sortBirthdayAscending) "birthdayDown" else "birthdayUp")
                }
            }
            Row {
                if (friends.isEmpty()){
                    Text(text = "No Friends")
                }
                LazyColumn(modifier = modifier) {
                    items(friends) { friend ->
                        FriendCard(friend, deleteFriend = deleteFriend, onNavigateToEditFriend = onNavigateToEditFriend)
                    }
                }
            }
        }
    }
}

@Composable
fun FriendCard(
    friend: Friend, modifier: Modifier = Modifier,
    deleteFriend: (Friend) -> Unit,
    onNavigateToEditFriend: (Friend) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = { expanded = !expanded }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Name: ${friend.name} \nAge: ${friend.age}", modifier = Modifier.weight(1f))

                IconButton(onClick = { onNavigateToEditFriend(friend) }) {
                    Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = { deleteFriend(friend) }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
            }
            Row {
                if (expanded) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Birthday: ${friend.birthDayOfMonth}/${friend.birthMonth}/${friend.birthYear}",
                        )
                }
            }

        }
    }
}

@Composable
fun AddFriendAction(onNavigateToAddFriend: () -> Unit) {
    FloatingActionButton(
        onClick = { onNavigateToAddFriend() },
    ) {
        Icon(Icons.Filled.Add, "Add a friend")
    }
}

@Preview(showBackground = true)
@Composable
fun BirthdayListPreview(){
    val previewFriends = listOf(
        Friend(
            id = 1,
            userId = "user123",
            name = "Alice Smith",
            birthYear = 1995,
            birthMonth = 7,
            birthDayOfMonth = 14,
            age = 29
        ),
        Friend(
            id = 2,
            userId = "user123",
            name = "Bob Johnson",
            birthYear = 1990,
            birthMonth = 12,
            birthDayOfMonth = 3,
            age = 34
        )
    )
    BirthdayList(
        onNavigateToAuthentication = {},
        onNavigateToAddFriend = {},
        onNavigateToEditFriend = {},
        userID = "lausbalber@gmail.com",
        friends = previewFriends,
        deleteFriend = {},
        sortByName = {},
        sortByAge = {},
        sortByBirthday = {},
        onFilterByName = {},
        onFilterByAge = {}
    )
}



