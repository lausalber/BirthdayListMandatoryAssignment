package com.example.birthdaylistmandatoryassignment.screens

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.birthdaylistmandatoryassignment.model.Friend
import java.util.Calendar

@SuppressLint("DefaultLocale")
@Composable
fun AddFriend(
    modifier: Modifier = Modifier,
    onNavigateToBirthdayList: () -> Unit,
    onNavigateBack: () -> Unit,
    createFriend: (Friend) -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    var day by remember { mutableStateOf(0) }
    var month by remember { mutableStateOf(0) }
    var year by remember { mutableStateOf(0) }
    val context = LocalContext.current

    var isDatePickerDialogOpen by remember { mutableStateOf(false) }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(text = "Add Friend", fontSize = 40.sp)
            }
            Row {
                OutlinedTextField(
                    modifier = modifier.padding(6.dp),
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") }
                )
            }
            Row {
                OutlinedTextField(
                    modifier = modifier.padding(6.dp),
                    value = if (day != 0) String.format("%02d/%02d/%04d", day, month, year) else "",
                    onValueChange = { },
                    label = { Text("Birthday") },
                    trailingIcon = {
                        IconButton(onClick = { isDatePickerDialogOpen = true }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Select date"
                            )
                        }
                    },
                    readOnly = true
                )
            }

            if (isDatePickerDialogOpen) {
                val calendar = Calendar.getInstance()
                val initialYear = calendar.get(Calendar.YEAR)
                val initialMonth = calendar.get(Calendar.MONTH)
                val initialDay = calendar.get(Calendar.DAY_OF_MONTH)

                DatePickerDialog(
                    context,
                    { _, selectedYear, selectedMonth, selectedDay ->
                        year = selectedYear
                        month = selectedMonth + 1  // Months are zero-based, so add 1
                        day = selectedDay
                        isDatePickerDialogOpen = false  // Close dialog after selection
                    },
                    initialYear,
                    initialMonth,
                    initialDay
                ).show()
            }

            Row {
                Button(onClick = {
                    onNavigateToBirthdayList(); createFriend(
                    (Friend(
                        id = 0,
                        userId = "Laus",
                        name = name,
                        birthYear = year,
                        birthMonth = month,
                        birthDayOfMonth = day,
                        age = 0
                    ))
                )
                }) {
                    Text(text = "Add Friend")
                }
            }
            Row {
                Button(onClick = { onNavigateBack() }) {
                    Text(text = "Cancel")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddFriendPreview() {
    AddFriend(
        onNavigateToBirthdayList = { },
        onNavigateBack = { }
    )
}