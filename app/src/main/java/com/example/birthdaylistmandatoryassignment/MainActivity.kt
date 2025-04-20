package com.example.birthdaylistmandatoryassignment

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.birthdaylistmandatoryassignment.model.AuthenticationViewModel
import com.example.birthdaylistmandatoryassignment.model.Friend
import com.example.birthdaylistmandatoryassignment.model.FriendsViewModel
import com.example.birthdaylistmandatoryassignment.ui.theme.BirthdayListMandatoryAssignmentTheme
import com.example.birthdaylistmandatoryassignment.screens.AddFriend
import com.example.birthdaylistmandatoryassignment.screens.AuthenticationScreen
import com.example.birthdaylistmandatoryassignment.screens.BirthdayList
import com.example.birthdaylistmandatoryassignment.screens.EditFriend
import com.example.birthdaylistmandatoryassignment.screens.TopBar
import com.google.firebase.auth.FirebaseUser

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BirthdayListMandatoryAssignmentTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val viewModelFriends: FriendsViewModel = viewModel()
    val viewModelAuth: AuthenticationViewModel = viewModel()
    val friends = viewModelFriends.friends.value
    val errorMessage = viewModelFriends.errorMessage.value
    val user by viewModelAuth.user.collectAsState()

    Scaffold(topBar = {
        TopBar(
            user = user,
            logOut = { viewModelAuth.signOut()
                navController.navigate(NavRoutes.Authentication.route)},
            onNavigateToAuthentication = { navController.navigate(NavRoutes.Authentication.route) }
        )
    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavRoutes.Authentication.route,
            //startDestination = if (user != null) NavRoutes.BirthdayList.route else NavRoutes.Authentication.route,
            modifier = modifier.padding(innerPadding)
        ) {

            composable(NavRoutes.AddFriend.route) {
                AddFriend(
                    onNavigateToBirthdayList = { navController.navigate(NavRoutes.BirthdayList.route) },
                    onNavigateBack = { navController.popBackStack() },
                    createFriend = { friend -> viewModelFriends.addFriend(friend) },
                    user = user)
            }

            composable(NavRoutes.BirthdayList.route) {
                BirthdayList(
                    onNavigateToAuthentication = { navController.navigate(NavRoutes.Authentication.route) },
                    onNavigateToAddFriend = { navController.navigate(NavRoutes.AddFriend.route) },
                    onNavigateToEditFriend = { friend ->
                        navController.navigate(NavRoutes.EditFriend.route + "/${friend.id}")},
                    deleteFriend = { friend: Friend -> viewModelFriends.deleteFriend(friend) },
                    friends = friends,
                    userID = user?.uid,
                    sortByName = { ascending -> viewModelFriends.sortFriendsByName(ascending) },
                    sortByAge = { ascending -> viewModelFriends.sortFriendsByAge(ascending) },
                    sortByBirthday = { ascending -> viewModelFriends.sortFriendsByBirthday(ascending) },
                    onFilterByName = { name -> viewModelFriends.filterByName(name) },
                    onFilterByAge = { age -> viewModelFriends.filterByAge(age) }
                )
            }

            composable(NavRoutes.EditFriend.route + "/{id}") { backstackEntry ->
                val id = backstackEntry.arguments?.getString("id")?.toIntOrNull()
                val friend = friends.find { it.id == id } ?: return@composable

                EditFriend(
                    onNavigateToBirthdayList = { navController.navigate(NavRoutes.BirthdayList.route) },
                    onNavigateBack = { navController.popBackStack() },
                    updateFriend = { friendId: Int, friend: Friend ->
                        viewModelFriends.updateFriend(friendId, friend)
                    },
                    friend = friend
                )
            }

            composable(NavRoutes.Authentication.route) {
                AuthenticationScreen(
                    user = user,
                    message = errorMessage,
                    signIn = { email: String, password: String ->
                        viewModelAuth.signIn(email, password)
                    },
                    register = { email: String, password: String ->
                        viewModelAuth.register(email, password)
                    },
                    navigateToBirthdayList = { navController.navigate(NavRoutes.BirthdayList.route) }
                )
            }
        }
    }
}