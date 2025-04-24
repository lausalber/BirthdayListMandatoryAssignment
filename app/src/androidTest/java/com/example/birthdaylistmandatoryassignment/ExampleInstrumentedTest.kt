package com.example.birthdaylistmandatoryassignment

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.birthdaylistmandatoryassignment.screens.AddFriend

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testEmptyNameErrorMessage() {

        composeTestRule.setContent {
            AddFriend(
                onNavigateBack = {},
                onNavigateToBirthdayList = {},
            )
        }

        composeTestRule.onNodeWithText("Name cannot be empty").assertDoesNotExist()

        composeTestRule.onNode(
            hasText("Add Friend")
                    and
                    hasClickAction()
        ).performClick()

        composeTestRule.onNodeWithText("Name cannot be empty").assertIsDisplayed()
    }
}