package com.example.birthdaylistmandatoryassignment.repository

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.birthdaylistmandatoryassignment.model.Friend
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Callback
import retrofit2.Response

class FriendsRepository() {
    private val auth = FirebaseAuth.getInstance()
    var user: FirebaseUser? by mutableStateOf(auth.currentUser)
        private set
    private val baseUrl = "https://birthdaysrest.azurewebsites.net/api/"
    private val friendsService: FriendsService
    val friends: MutableState<List<Friend>> = mutableStateOf(listOf())
    val errorMessage = mutableStateOf("")

    private val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
        user = firebaseAuth.currentUser
        getMyFriends()
    }

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        friendsService = build.create(FriendsService::class.java)

        auth.addAuthStateListener(authStateListener)
    }

    fun getFriends() {
        friendsService.getAllFriends().enqueue(object : Callback<List<Friend>> {
            override fun onResponse(call: Call<List<Friend>>, response: Response<List<Friend>>) {
                if (response.isSuccessful) {
                    val friendList: List<Friend>? = response.body()
                    friends.value = friendList ?: emptyList()
                    errorMessage.value = ""
                    Log.d("APPLE", "Friends were successfully fetched")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.value = message
                    Log.e("APPLE", "Fetch failed: $message")
                }
            }

            override fun onFailure(call: Call<List<Friend>>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
                errorMessage.value = message
                Log.e("APPLE", message)
            }
        })
    }

    fun getMyFriends() {
        val currentUser = user
        if (currentUser == null) {
            errorMessage.value = "User is null"
            Log.e("APPLE", "Could not fetch friends: User is null")
            return
        }

        friendsService.getAllMyFriends(currentUser.email).enqueue(object : Callback<List<Friend>> {
            override fun onResponse(call: Call<List<Friend>>, response: Response<List<Friend>>) {
                if (response.isSuccessful) {
                    val friendList: List<Friend>? = response.body()
                    friends.value = friendList ?: emptyList()
                    errorMessage.value = ""
                    Log.d("APPLE", "Friends were successfully fetched")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.value = message
                    Log.e("APPLE", "Friends were not fetched: $message")
                }
            }

            override fun onFailure(call: Call<List<Friend>>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
                errorMessage.value = message
                Log.e("APPLE", "Friends were not fetched: $message")
            }
        })
    }

    fun add(friend: Friend) {
        friendsService.createFriend(friend).enqueue(object : Callback<Friend> {
            override fun onResponse(call: Call<Friend>, response: Response<Friend>) {
                if (response.isSuccessful) {
                    errorMessage.value = ""
                    Log.d("APPLE", "Add: " + response.body())
                    getMyFriends()
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.value = message
                    Log.e("APPLE", "Not added: $message")
                }
            }

            override fun onFailure(call: Call<Friend>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
                errorMessage.value = message
                Log.e("APPLE", "Not added: $message")
            }
        })
    }

    fun delete(friend: Friend) {
        friendsService.deleteFriend(friend.id).enqueue(object : Callback<Friend> {
            override fun onResponse(call: Call<Friend>, response: Response<Friend>) {
                if (response.isSuccessful) {
                    errorMessage.value = ""
                    Log.d("APPLE", "Friend was deleted: " + response.body())
                    getMyFriends()
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.value = message
                    Log.e("APPLE", "Not deleted: $message $friend.id")
                }
            }

            override fun onFailure(call: Call<Friend>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
                errorMessage.value = message
                Log.e("APPLE", "Not deleted: $message")
            }
        })
    }

    fun update(friendId: Int?, friend: Friend) {
        friendsService.updateFriend(friendId, friend).enqueue(object : Callback<Friend> {
            override fun onResponse(call: Call<Friend>, response: Response<Friend>) {
                if (response.isSuccessful) {
                    errorMessage.value = ""
                    Log.d("APPLE", "Update successful: " + response.body())
                    getMyFriends()
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.value = message
                    Log.e("APPLE", "Update wasn't successful: $message")
                }
            }

            override fun onFailure(call: Call<Friend>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
                errorMessage.value = message
                Log.e("APPLE", "Update wasn't successful: $message")
            }
        })
    }

    fun sortByName(ascending: Boolean) {
        friends.value = if (ascending) {
            friends.value.sortedBy { it.name.lowercase() }
        } else {
            friends.value.sortedByDescending { it.name.lowercase() }
        }
    }

    fun sortByAge(ascending: Boolean) {
        friends.value = if (ascending) {
            friends.value.sortedBy { it.age }
        } else {
            friends.value.sortedByDescending { it.age }
        }
    }

    fun sortByBirthday(ascending: Boolean) {
        friends.value = if (ascending) {
            friends.value.sortedWith(
                compareBy(
                    { it.birthYear },
                    { it.birthMonth },
                    { it.birthDayOfMonth })
            )
        } else {
            friends.value.sortedWith(compareByDescending<Friend> { it.birthYear }
                .thenByDescending { it.birthMonth }
                .thenByDescending { it.birthDayOfMonth })
        }
    }

    fun filterByName(nameFragment: String) {
        if (nameFragment.isEmpty()) {
            getMyFriends()
            return
        }
        friends.value =
            friends.value.filter {
                it.name.contains(nameFragment, ignoreCase = true)
            }
    }

    fun filterByAge(age: Int) {
        if (age == 0) {
            getMyFriends()
            return
        }
        friends.value = friends.value.filter {
            it.age == age
        }
    }
}