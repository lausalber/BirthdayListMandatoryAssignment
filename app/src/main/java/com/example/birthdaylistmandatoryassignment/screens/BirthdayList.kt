package com.example.birthdaylistmandatoryassignment.screens

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.birthdaylistmandatoryassignment.model.Friend

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BirthdayList(
    modifier: Modifier = Modifier,
    onNavigateToAddFriend: () -> Unit,
    onNavigateToEditFriend: (Friend) -> Unit,
    friends: List<Friend>,
    deleteFriend: (Friend) -> Unit,
    sortByName: (Boolean) -> Unit,
    sortByAge: (Boolean) -> Unit,
    sortByBirthday: (Boolean) -> Unit,
    onFilterByName: (String) -> Unit,
    onFilterByAge: (Int) -> Unit
) {
    var sortNameAscending by rememberSaveable { mutableStateOf(true) }
    var sortAgeAscending by rememberSaveable { mutableStateOf(true) }
    var sortBirthdayAscending by rememberSaveable { mutableStateOf(true) }
    var nameFragment by rememberSaveable { mutableStateOf("") }
    var ageFragment by rememberSaveable { mutableStateOf("") }

    val configuration = LocalConfiguration.current

    Scaffold(
        floatingActionButton = { AddFriendAction { onNavigateToAddFriend() } })
    {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                VerticalBirthdayList(
                    modifier = modifier,
                    onNavigateToEditFriend = onNavigateToEditFriend,
                    friends = friends,
                    deleteFriend = deleteFriend,
                    sortByName = sortByName,
                    sortByAge = sortByAge,
                    sortByBirthday = sortByBirthday,
                    onFilterByName = onFilterByName,
                    onFilterByAge = onFilterByAge,
                    nameFragment = nameFragment,
                    onNameFragmentChange = { nameFragment = it },
                    ageFragment = ageFragment,
                    onAgeFragmentChange = { ageFragment = it },
                    sortNameAscending = sortNameAscending,
                    onSortNameAscendingChange = { sortNameAscending = it },
                    sortAgeAscending = sortAgeAscending,
                    onSortAgeAscendingChange = { sortAgeAscending = it },
                    sortBirthdayAscending = sortBirthdayAscending,
                    onSortBirthdayAscendingChange = { sortBirthdayAscending = it }
                )
            }
            if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                HorizontalBirthdayList(
                    modifier = modifier,
                    onNavigateToEditFriend = onNavigateToEditFriend,
                    friends = friends,
                    deleteFriend = deleteFriend,
                    sortByName = sortByName,
                    sortByAge = sortByAge,
                    sortByBirthday = sortByBirthday,
                    onFilterByName = onFilterByName,
                    onFilterByAge = onFilterByAge,
                    nameFragment = nameFragment,
                    onNameFragmentChange = { nameFragment = it },
                    ageFragment = ageFragment,
                    onAgeFragmentChange = { ageFragment = it },
                    sortNameAscending = sortNameAscending,
                    onSortNameAscendingChange = { sortNameAscending = it },
                    sortAgeAscending = sortAgeAscending,
                    onSortAgeAscendingChange = { sortAgeAscending = it },
                    sortBirthdayAscending = sortBirthdayAscending,
                    onSortBirthdayAscendingChange = { sortBirthdayAscending = it })
            }
        }
    }
}

@Composable
fun VerticalBirthdayList(
    modifier: Modifier,
    onNavigateToEditFriend: (Friend) -> Unit,
    friends: List<Friend>,
    deleteFriend: (Friend) -> Unit,
    sortByName: (Boolean) -> Unit,
    sortByAge: (Boolean) -> Unit,
    sortByBirthday: (Boolean) -> Unit,
    onFilterByName: (String) -> Unit,
    onFilterByAge: (Int) -> Unit,
    nameFragment: String,
    onNameFragmentChange: (String) -> Unit,
    ageFragment: String,
    onAgeFragmentChange: (String) -> Unit,
    sortNameAscending: Boolean,
    onSortNameAscendingChange: (Boolean) -> Unit,
    sortAgeAscending: Boolean,
    onSortAgeAscendingChange: (Boolean) -> Unit,
    sortBirthdayAscending: Boolean,
    onSortBirthdayAscendingChange: (Boolean) -> Unit
) {
    Row {
        OutlinedTextField(
            value = nameFragment,
            onValueChange = { onNameFragmentChange(it) },
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
                    onAgeFragmentChange(input)
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
            onSortNameAscendingChange(!sortNameAscending)
        }) {
            Text(text = if (sortNameAscending) "Name Down" else "Name Up")
        }
        TextButton(onClick = {
            sortByAge(sortAgeAscending)
            onSortAgeAscendingChange(!sortAgeAscending)
        }) {
            Text(text = if (sortAgeAscending) "Age Down" else "Age Up")
        }
        TextButton(onClick = {
            sortByBirthday(sortBirthdayAscending)
            onSortBirthdayAscendingChange(!sortBirthdayAscending)
        }) {
            Text(text = if (sortBirthdayAscending) "Birthday Down" else "Birthday Up")
        }
    }

    Row {
        if (friends.isEmpty()) {
            Text(text = "No Friends")
        }
        LazyColumn(modifier = modifier) {
            items(friends) { friend ->
                FriendCard(
                    friend,
                    deleteFriend = deleteFriend,
                    onNavigateToEditFriend = onNavigateToEditFriend
                )
            }
        }
    }
}

@Composable
fun HorizontalBirthdayList(
    modifier: Modifier,
    onNavigateToEditFriend: (Friend) -> Unit,
    friends: List<Friend>,
    deleteFriend: (Friend) -> Unit,
    sortByName: (Boolean) -> Unit,
    sortByAge: (Boolean) -> Unit,
    sortByBirthday: (Boolean) -> Unit,
    onFilterByName: (String) -> Unit,
    onFilterByAge: (Int) -> Unit,
    nameFragment: String,
    onNameFragmentChange: (String) -> Unit,
    ageFragment: String,
    onAgeFragmentChange: (String) -> Unit,
    sortNameAscending: Boolean,
    onSortNameAscendingChange: (Boolean) -> Unit,
    sortAgeAscending: Boolean,
    onSortAgeAscendingChange: (Boolean) -> Unit,
    sortBirthdayAscending: Boolean,
    onSortBirthdayAscendingChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(0.4f)
                .padding(end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    modifier = Modifier.widthIn(max = 500.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = nameFragment,
                            onValueChange = onNameFragmentChange,
                            label = { Text("Filter by name") },
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = { onFilterByName(nameFragment) }
                        ) {
                            Text("Filter")
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = ageFragment,
                            onValueChange = { input ->
                                if (input.all { it.isDigit() }) {
                                    onAgeFragmentChange(input)
                                }
                            },
                            label = { Text("Filter by age") },
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                onFilterByAge(ageFragment.toIntOrNull() ?: 0)
                            }
                        ) {
                            Text("Filter")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                Row {
                    TextButton(onClick = {
                        sortByName(sortNameAscending)
                        onSortNameAscendingChange(!sortNameAscending)
                    }) {
                        Text(text = if (sortNameAscending) "Name Down" else "Name Up")
                    }
                    TextButton(onClick = {
                        sortByAge(sortAgeAscending)
                        onSortAgeAscendingChange(!sortAgeAscending)
                    }) {
                        Text(text = if (sortAgeAscending) "Age Down" else "Age Up")
                    }
                    TextButton(onClick = {
                        sortByBirthday(sortBirthdayAscending)
                        onSortBirthdayAscendingChange(!sortBirthdayAscending)
                    }) {
                        Text(text = if (sortBirthdayAscending) "Birthday Down" else "Birthday Up")
                    }
                }

            }
        }

        Column(
            modifier = Modifier
                .weight(0.6f)
                .fillMaxHeight()
        ) {
            if (friends.isEmpty()) {
                Text(text = "No Friends")
            }
            LazyColumn(modifier = modifier.fillMaxSize()) {
                items(friends) { friend ->
                    FriendCard(
                        friend,
                        deleteFriend = deleteFriend,
                        onNavigateToEditFriend = onNavigateToEditFriend
                    )
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
    var expanded by rememberSaveable { mutableStateOf(false) }
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
                Text(
                    text = "Name: ${friend.name} \nAge: ${friend.age}",
                    modifier = Modifier.weight(1f)
                )

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

@Composable
@Preview(showBackground = true)
fun VerticalBirthdayListPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        VerticalBirthdayList(
            modifier = Modifier,
            onNavigateToEditFriend = {},
            friends = previewFriends,
            deleteFriend = {},
            sortByName = {},
            sortByAge = {},
            sortByBirthday = {},
            onFilterByName = {},
            onFilterByAge = {},
            nameFragment = "",
            onNameFragmentChange = {},
            ageFragment = "",
            onAgeFragmentChange = {},
            sortNameAscending = true,
            onSortNameAscendingChange = {},
            sortAgeAscending = true,
            onSortAgeAscendingChange = {},
            sortBirthdayAscending = true,
            onSortBirthdayAscendingChange = {},
        )
    }
}

@Composable
@Preview(
    showBackground = true,
    widthDp = 800,
    heightDp = 350
)
fun HorizontalBirthdayListPreview() {
    HorizontalBirthdayList(
        modifier = Modifier,
        onNavigateToEditFriend = {},
        friends = previewFriends,
        deleteFriend = {},
        sortByName = {},
        sortByAge = {},
        sortByBirthday = {},
        onFilterByName = {},
        onFilterByAge = {},
        nameFragment = "",
        onNameFragmentChange = {},
        ageFragment = "",
        onAgeFragmentChange = {},
        sortNameAscending = true,
        onSortNameAscendingChange = {},
        sortAgeAscending = true,
        onSortAgeAscendingChange = {},
        sortBirthdayAscending = true,
        onSortBirthdayAscendingChange = {},
    )
}

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