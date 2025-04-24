package com.example.birthdaylistmandatoryassignment.screens

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.birthdaylistmandatoryassignment.model.Friend
import com.google.firebase.auth.FirebaseUser
import java.util.Calendar

@Composable
fun EditFriend(
    modifier: Modifier = Modifier,
    user: FirebaseUser?,
    friend: Friend,
    onNavigateToBirthdayList: () -> Unit,
    onNavigateBack: () -> Unit,
    updateFriend: (Int, Friend) -> Unit,
) {
    var name by remember { mutableStateOf(friend.name) }
    var day by remember { mutableIntStateOf(friend.birthDayOfMonth) }
    var month by remember { mutableIntStateOf(friend.birthMonth) }
    var year by remember { mutableIntStateOf(friend.birthYear) }
    var isDatePickerDialogOpen by remember { mutableStateOf(false) }

    val configuration = LocalConfiguration.current

    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT) {
                VerticalEditFriend(
                    user = user,
                    name = name,
                    onNameChange = { name = it },
                    day = day,
                    month = month,
                    year = year,
                    isDatePickerDialogOpen = isDatePickerDialogOpen,
                    onDatePickerDialogOpenChange = { isDatePickerDialogOpen = it },
                    onDateChange = { newDay, newMonth, newYear ->
                        day = newDay
                        month = newMonth
                        year = newYear
                    },
                    onNavigateToBirthdayList = onNavigateToBirthdayList,
                    onNavigateBack = onNavigateBack,
                    updateFriend = { updated ->
                        updateFriend(friend.id, updated)
                    }
                )
            }
            if (configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
                HorizontalEditFriend(
                    user = user,
                    name = name,
                    onNameChange = { name = it },
                    day = day,
                    month = month,
                    year = year,
                    isDatePickerDialogOpen = isDatePickerDialogOpen,
                    onDatePickerDialogOpenChange = { isDatePickerDialogOpen = it },
                    onDateChange = { newDay, newMonth, newYear ->
                        day = newDay
                        month = newMonth
                        year = newYear
                    },
                    onNavigateToBirthdayList = onNavigateToBirthdayList,
                    onNavigateBack = onNavigateBack,
                    updateFriend = { updated ->
                        updateFriend(friend.id, updated)
                    }
                )
            }
        }
    }
}


@Composable
fun VerticalEditFriend(
    user: FirebaseUser?,
    name: String,
    onNameChange: (String) -> Unit,
    day: Int,
    month: Int,
    year: Int,
    isDatePickerDialogOpen: Boolean,
    onDatePickerDialogOpenChange: (Boolean) -> Unit,
    onDateChange: (Int, Int, Int) -> Unit,
    onNavigateToBirthdayList: () -> Unit,
    onNavigateBack: () -> Unit,
    updateFriend: (Friend) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Edit Friend", fontSize = 40.sp)

        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Name") },
            modifier = Modifier
                .padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = if (day != 0) String.format("%02d/%02d/%04d", day, month, year) else "",
            onValueChange = {},
            label = { Text("Birthday") },
            trailingIcon = {
                IconButton(onClick = { onDatePickerDialogOpenChange(true) }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Pick date")
                }
            },
            readOnly = true,
            modifier = Modifier
                .padding(vertical = 8.dp)
        )

        Row(
            modifier = Modifier.padding(top = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(onClick = onNavigateBack) {
                Text("Cancel")
            }

            Button(onClick = {
                updateFriend(Friend(0, user!!.email.toString(), name, year, month, day, 0))
                onNavigateToBirthdayList()
            }) {
                Text("Edit Friend")
            }
        }
    }

    if (isDatePickerDialogOpen) {
        val calendar = Calendar.getInstance()
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            context,
            { _, y, m, d ->
                onDateChange(d, m + 1, y)
                onDatePickerDialogOpenChange(false)
            },
            initialYear,
            initialMonth,
            initialDay
        ).show()
    }
}


@Composable
fun HorizontalEditFriend(
    user: FirebaseUser?,
    name: String,
    onNameChange: (String) -> Unit,
    day: Int,
    month: Int,
    year: Int,
    isDatePickerDialogOpen: Boolean,
    onDatePickerDialogOpenChange: (Boolean) -> Unit,
    onDateChange: (Int, Int, Int) -> Unit,
    onNavigateToBirthdayList: () -> Unit,
    onNavigateBack: () -> Unit,
    updateFriend: (Friend) -> Unit
) {
    val context = LocalContext.current

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
            Text(text = "Edit Friend", fontSize = 40.sp)

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = onNameChange,
                    label = { Text("Name") },
                    modifier = Modifier.width(250.dp)
                )

                OutlinedTextField(
                    value = if (day != 0) String.format("%02d/%02d/%04d", day, month, year) else "",
                    onValueChange = {},
                    label = { Text("Birthday") },
                    trailingIcon = {
                        IconButton(onClick = { onDatePickerDialogOpenChange(true) }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Pick date"
                            )
                        }
                    },
                    readOnly = true,
                    modifier = Modifier.width(250.dp)
                )
            }

            // Row with buttons side-by-side
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(onClick = onNavigateBack) {
                    Text("Cancel")
                }

                Button(onClick = {
                    updateFriend(Friend(0, user!!.email.toString(), name, year, month, day, 0))
                    onNavigateToBirthdayList()
                }) {
                    Text("Edit Friend")
                }
            }
        }
    }

    if (isDatePickerDialogOpen) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            context,
            { _, y, m, d ->
                onDateChange(d, m + 1, y)
                onDatePickerDialogOpenChange(false)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}


@Preview(showBackground = true)
@Composable
fun EditFriendVerticalPreview() {
    VerticalEditFriend(
        user = null,
        name = "",
        onNameChange = { },
        day = 0,
        month = 0,
        year = 0,
        isDatePickerDialogOpen = false,
        onDatePickerDialogOpenChange = { },
        onDateChange = { _, _, _ -> },
        onNavigateToBirthdayList = { },
        onNavigateBack = { },
        updateFriend = { }
    )
}

@Preview(showBackground = true, widthDp = 800, heightDp = 350)
@Composable
fun EditFriendHorizontalPreview() {
    HorizontalEditFriend(
        user = null,
        name = "",
        onNameChange = { },
        day = 0,
        month = 0,
        year = 0,
        isDatePickerDialogOpen = false,
        onDatePickerDialogOpenChange = { },
        onDateChange = { _, _, _ -> },
        onNavigateToBirthdayList = { },
        onNavigateBack = { },
        updateFriend = { }
    )
}