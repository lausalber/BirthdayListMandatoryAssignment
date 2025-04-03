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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.birthdaylistmandatoryassignment.model.Friend
import com.google.firebase.auth.FirebaseUser

@Composable
fun BirthdayList(
    modifier: Modifier = Modifier,
    onNavigateToLogIn: () -> Unit,
    onNavigateToAddFriend: () -> Unit,
    onNavigateToEditFriend: (Friend) -> Unit,
    onNavigateBack: () -> Unit,
    userID: String?,
    getMyFriends: (userID: String?) -> Unit,
    friends: List<Friend>,
    deleteFriend: (Friend) -> Unit,
) {
    getMyFriends(userID)
    Scaffold(
        floatingActionButton = { AddFriendAction { onNavigateToAddFriend() } })
    { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
        ) {
            Row {
                Text("some sort and filters")
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
                Text(text = friend.name, modifier = Modifier.weight(1f))

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
                        text = "Age: ${friend.age}",
                    )
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



