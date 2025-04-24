package com.example.birthdaylistmandatoryassignment.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.res.Configuration
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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

@SuppressLint("DefaultLocale")
@Composable
fun AddFriend(
    modifier: Modifier = Modifier,
    onNavigateToBirthdayList: () -> Unit,
    onNavigateBack: () -> Unit,
    createFriend: (Friend) -> Unit = {},
    user: FirebaseUser? = null
) {
    var name by rememberSaveable { mutableStateOf("") }
    var day by rememberSaveable { mutableIntStateOf(0) }
    var month by rememberSaveable { mutableIntStateOf(0) }
    var year by rememberSaveable { mutableIntStateOf(0) }
    var nameIsError by rememberSaveable { mutableStateOf(false) }
    var dateIsError by rememberSaveable { mutableStateOf(false) }

    var isDatePickerDialogOpen by rememberSaveable { mutableStateOf(false) }

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
                VerticalAddFriend(
                    modifier = modifier,
                    onNavigateBack = onNavigateBack,
                    onCreateFriendClick = {
                        nameIsError = name.isEmpty()
                        dateIsError = day == 0 || month == 0 || year == 0

                        if (!nameIsError && !dateIsError) {
                            createFriend(
                                Friend(
                                    id = 0,
                                    userId = user?.email.orEmpty(),
                                    name = name,
                                    birthYear = year,
                                    birthMonth = month,
                                    birthDayOfMonth = day,
                                    age = 0
                                )
                            )
                            onNavigateToBirthdayList()
                        }
                    },
                    name = name,
                    onNameChange = { name = it },
                    day = day,
                    month = month,
                    year = year,
                    onDateChange = { d, m, y ->
                        day = d
                        month = m
                        year = y
                    },
                    isDatePickerDialogOpen = isDatePickerDialogOpen,
                    onDatePickerDialogOpenChange = { isDatePickerDialogOpen = it },
                    nameIsError = nameIsError,
                    dateIsError = dateIsError
                )
            }

            if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                HorizontalAddFriend(
                    modifier = modifier,
                    onNavigateBack = onNavigateBack,
                    onCreateFriendClick = {
                        nameIsError = name.isEmpty()
                        dateIsError = day == 0 || month == 0 || year == 0

                        if (!nameIsError && !dateIsError) {
                            createFriend(
                                Friend(
                                    id = 0,
                                    userId = user?.email.orEmpty(),
                                    name = name,
                                    birthYear = year,
                                    birthMonth = month,
                                    birthDayOfMonth = day,
                                    age = 0
                                )
                            )
                            onNavigateToBirthdayList()
                        }
                    },
                    name = name,
                    onNameChange = { name = it },
                    day = day,
                    month = month,
                    year = year, onDateChange = { d, m, y ->
                        day = d
                        month = m
                        year = y
                    },
                    isDatePickerDialogOpen = isDatePickerDialogOpen,
                    onDatePickerDialogOpenChange = { isDatePickerDialogOpen = it },
                    nameIsError = nameIsError,
                    dateIsError = dateIsError
                )
            }
        }
    }
}

@Composable
fun VerticalAddFriend(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onCreateFriendClick: () -> Unit,
    name: String,
    onNameChange: (String) -> Unit,
    day: Int,
    month: Int,
    year: Int,
    onDateChange: (Int, Int, Int) -> Unit,
    isDatePickerDialogOpen: Boolean,
    onDatePickerDialogOpenChange: (Boolean) -> Unit,
    nameIsError: Boolean,
    dateIsError: Boolean,
) {
    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Add Friend", fontSize = 40.sp)

        OutlinedTextField(
            modifier = modifier.padding(6.dp),
            value = name,
            onValueChange = onNameChange,
            label = { Text("Name") },
        )
        if (nameIsError) {
            Text(
                text = "Name cannot be empty",
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp
            )
        }

        OutlinedTextField(
            modifier = modifier.padding(6.dp),
            value = if (day != 0) String.format("%02d/%02d/%04d", day, month, year) else "",
            onValueChange = {},
            label = { Text("Birthday") },
            trailingIcon = {
                IconButton(onClick = { onDatePickerDialogOpenChange(true) }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            readOnly = true
        )

        if (dateIsError) {
            Text(
                text = "Birthday cannot be empty",
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp
            )
        }
        Row(
            modifier = Modifier.padding(top = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(onClick = onNavigateBack) {
                Text(text = "Cancel")
            }

            Button(onClick = {
                onCreateFriendClick()
            }) {
                Text(text = "Add Friend")
            }

        }
    }

    if (isDatePickerDialogOpen) {
        val calendar = Calendar.getInstance()
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            LocalContext.current,
            { _, selectedYear, selectedMonth, selectedDay ->
                onDateChange(selectedDay, selectedMonth + 1, selectedYear)
                onDatePickerDialogOpenChange(false)
            },
            initialYear,
            initialMonth,
            initialDay
        ).show()
    }
}


@Composable
fun HorizontalAddFriend(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onCreateFriendClick: () -> Unit,
    name: String,
    onNameChange: (String) -> Unit,
    day: Int,
    month: Int,
    year: Int,
    onDateChange: (Int, Int, Int) -> Unit,
    isDatePickerDialogOpen: Boolean,
    onDatePickerDialogOpenChange: (Boolean) -> Unit,
    nameIsError: Boolean,
    dateIsError: Boolean
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
            Text(text = "Add Friend", fontSize = 40.sp)

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    OutlinedTextField(
                        value = name,
                        onValueChange = onNameChange,
                        label = { Text("Name") },
                        modifier = modifier.width(250.dp)
                    )
                    if (nameIsError) {
                        Text(
                            text = "Name cannot be empty",
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp
                        )
                    }
                }

                Column {
                    OutlinedTextField(
                        value = if (day != 0) String.format(
                            "%02d/%02d/%04d",
                            day,
                            month,
                            year
                        ) else "",
                        onValueChange = {},
                        label = { Text("Birthday") },
                        trailingIcon = {
                            IconButton(onClick = { onDatePickerDialogOpenChange(true) }) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = "Select date"
                                )
                            }
                        },
                        readOnly = true,
                        modifier = modifier.width(250.dp)
                    )
                    if (dateIsError) {
                        Text(
                            text = "Birthday cannot be empty",
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(onClick = onNavigateBack) {
                    Text("Cancel")
                }

                Button(onClick = onCreateFriendClick) {
                    Text("Add Friend")
                }
            }
        }

        if (isDatePickerDialogOpen) {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                LocalContext.current,
                { _, selectedYear, selectedMonth, selectedDay ->
                    onDateChange(selectedDay, selectedMonth + 1, selectedYear)
                    onDatePickerDialogOpenChange(false)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VerticalAddFriendPreview() {
    VerticalAddFriend(
        onNavigateBack = { },
        name = "",
        onNameChange = { },
        day = 0,
        month = 0,
        year = 0,
        onDateChange = { _, _, _ -> },
        isDatePickerDialogOpen = false,
        onDatePickerDialogOpenChange = { },
        nameIsError = false,
        dateIsError = false,
        onCreateFriendClick = { },
    )
}

@Composable
@Preview(
    showBackground = true,
    widthDp = 800,
    heightDp = 350
)
fun HorizontalAddFriendPreview() {
    HorizontalAddFriend(
        onNavigateBack = { },
        name = "",
        onNameChange = { },
        day = 0,
        month = 0,
        year = 0,
        onDateChange = { _, _, _ -> },
        isDatePickerDialogOpen = false,
        onDatePickerDialogOpenChange = { },
        nameIsError = false,
        dateIsError = false,
        onCreateFriendClick = { },
    )
}