package com.example.birthdaylistmandatoryassignment.repository

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.birthdaylistmandatoryassignment.model.Friend
import com.google.firebase.auth.FirebaseUser
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Callback
import retrofit2.Response

class FriendsRepository() {
    private val baseUrl = "https://birthdaysrest.azurewebsites.net/api/"


    private val friendsService: FriendsService
    val friends: MutableState<List<Friend>> = mutableStateOf(listOf())
    val errorMessage = mutableStateOf("")

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // GSON
            .build()
        friendsService = build.create(FriendsService::class.java)
    }

    fun getFriends() {
        friendsService.getAllFriends().enqueue(object : Callback<List<Friend>> {
            override fun onResponse(call: Call<List<Friend>>, response: Response<List<Friend>>) {
                if (response.isSuccessful) {
                    val friendList: List<Friend>? = response.body()
                    friends.value = friendList ?: emptyList()
                    errorMessage.value = ""
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.value = message
                    Log.e("APPLE", message)
                }
            }

            override fun onFailure(call: Call<List<Friend>>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
                errorMessage.value = message
                Log.e("APPLE", message)
            }
        })
    }

    fun getMyFriends(userID : String?) {
        friendsService.getAllMyFriends(userID).enqueue(object : Callback<List<Friend>> {
            override fun onResponse(call: Call<List<Friend>>, response: Response<List<Friend>>) {
                if (response.isSuccessful) {
                    val friendList: List<Friend>? = response.body()
                    friends.value = friendList ?: emptyList()
                    errorMessage.value = ""
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.value = message
                    Log.e("APPLE", message)
                }
            }

            override fun onFailure(call: Call<List<Friend>>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
                errorMessage.value = message
                Log.e("APPLE", message)
            }
        })
    }

    fun add(friend: Friend) {
        friendsService.createFriend(friend).enqueue(object : Callback<Friend> {
            override fun onResponse(call: Call<Friend>, response: Response<Friend>) {
                if (response.isSuccessful) {
                    errorMessage.value = ""
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.value = message
                }
            }

            override fun onFailure(call: Call<Friend>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
                errorMessage.value = message
            }
        })
    }

    fun delete(friend: Friend) {
        friendsService.deleteFriend(friend.id).enqueue(object : Callback<Friend> {
            override fun onResponse(call: Call<Friend>, response: Response<Friend>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Delete: " + response.body())
                    errorMessage.value = ""
                    Log.d("APPLE", friend.userId)
                    getMyFriends(friend.userId)
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.value = message
                    Log.e("APPLE", "Not deleted: $message $friend.id")
                }
            }

            override fun onFailure(call: Call<Friend>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
                errorMessage.value = message
                Log.e("APPLE", "Not deleted $message")
            }
        })
    }

    fun update(friendId : Int?, friend : Friend) {
        Log.d("APPLE", "Update: $friendId $friend")
        friendsService.updateFriend(friendId, friend).enqueue(object : Callback<Friend> {
            override fun onResponse(call: Call<Friend>, response: Response<Friend>) {
                if (response.isSuccessful) {
                    Log.d("APPLE", "Updated: " + response.body())
                    errorMessage.value = ""
                    Log.d("APPLE", "update successful")
                    getMyFriends(friend.userId)
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessage.value = message
                    Log.e("APPLE", "Update $message")
                }
            }

            override fun onFailure(call: Call<Friend>, t: Throwable) {
                val message = t.message ?: "No connection to back-end"
                errorMessage.value = message
                Log.e("APPLE", "Update $message")
            }
        })
    }
}