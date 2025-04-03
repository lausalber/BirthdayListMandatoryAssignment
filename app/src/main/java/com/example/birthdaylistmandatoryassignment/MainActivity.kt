package com.example.birthdaylistmandatoryassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
import com.example.birthdaylistmandatoryassignment.screens.BirthdayList
import com.example.birthdaylistmandatoryassignment.screens.EditFriend
import com.example.birthdaylistmandatoryassignment.screens.LogIn
import com.example.birthdaylistmandatoryassignment.screens.RegisterUser
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
    val userID = viewModelAuth.user?.uid

    Scaffold(topBar = {
        TopBar(
            user = userID,
            logOut = { viewModelAuth.signOut() },
            onNavigateToLogIn = { navController.navigate(NavRoutes.LogIn.route) }
        )
    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavRoutes.LogIn.route,
            modifier = modifier.padding(innerPadding)
        ) {

            composable(NavRoutes.AddFriend.route) {
                AddFriend(
                    onNavigateToBirthdayList = { navController.navigate(NavRoutes.BirthdayList.route) },
                    onNavigateBack = { navController.popBackStack() },
                    createFriend = { friend -> viewModelFriends.addFriend(friend) })
            }

            composable(NavRoutes.BirthdayList.route) {
                BirthdayList(
                    onNavigateToLogIn = { navController.navigate(NavRoutes.LogIn.route) },
                    onNavigateToAddFriend = { navController.navigate(NavRoutes.AddFriend.route) },
                    onNavigateToEditFriend = { friend ->
                        navController.navigate(NavRoutes.EditFriend.route + "/${friend.id}")
                    },
                    onNavigateBack = { navController.popBackStack() },
                    getMyFriends = { viewModelFriends.getMyFriends(userID) },
                    deleteFriend = { friend: Friend -> viewModelFriends.deleteFriend(friend) },
                    friends = friends,
                    userID = userID
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

            composable(NavRoutes.LogIn.route) {
                LogIn(
                    onNavigateToBirthdayList = { navController.navigate(NavRoutes.BirthdayList.route) },
                    onNavigateToRegisterUser = { navController.navigate(NavRoutes.RegisterUser.route) },
                    user = viewModelAuth.user,
                    logIn = { email: String, password: String -> viewModelAuth.signIn(email, password) },
                    message = viewModelAuth.message
                )
            }

            composable(NavRoutes.RegisterUser.route) {
                RegisterUser(
                    onNavigateToLogIn = { navController.navigate(NavRoutes.LogIn.route) },
                    onNavigateBack = { navController.popBackStack() },
                    authViewModel = viewModelAuth,
                    register = { email: String, password: String ->
                        viewModelAuth.register(email, password)
                    },
                    message = viewModelAuth.message
                )
            }

        }
    }
}