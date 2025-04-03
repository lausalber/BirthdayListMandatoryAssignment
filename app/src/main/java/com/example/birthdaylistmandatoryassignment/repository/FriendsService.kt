package com.example.birthdaylistmandatoryassignment.repository

import com.example.birthdaylistmandatoryassignment.model.Friend
import retrofit2.Call
import retrofit2.http.*

interface FriendsService {
    @GET("persons")
    fun getAllFriends(): Call<List<Friend>>

    @GET("persons")
    fun getAllMyFriends(@Query("user_id") userID: String?): Call<List<Friend>>

    @GET("persons/{friendId}")
    fun getFriendById(@Path("friendId") id: Int): Call<Friend>

    @POST("persons")
    fun createFriend(@Body friend: Friend): Call<Friend>

    @DELETE("persons/{friendId}")
    fun deleteFriend(@Path("friendId") id: Int): Call<Friend>

    @PUT("persons/{friendId}")
    fun updateFriend(@Path("friendId") id: Int?, @Body book: Friend): Call<Friend>
}