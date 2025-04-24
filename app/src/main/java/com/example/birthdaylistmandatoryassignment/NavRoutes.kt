package com.example.birthdaylistmandatoryassignment

sealed class NavRoutes(val route: String) {
    data object BirthdayList : NavRoutes("birthdayList")
    data object EditFriend : NavRoutes("editFriend")
    data object AddFriend : NavRoutes("addFriend")
    data object Authentication : NavRoutes("authentication")
}