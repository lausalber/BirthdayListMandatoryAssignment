package com.example.birthdaylistmandatoryassignment.model

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import com.example.birthdaylistmandatoryassignment.repository.FriendsRepository

class FriendsViewModel(): ViewModel(){
    private val repository = FriendsRepository()
    val friends: State<List<Friend>> = repository.friends
    val errorMessage: State<String> = repository.errorMessage

    fun getFriends(){
        repository.getFriends()
    }

    fun getMyFriends(userID : String?){
        repository.getMyFriends(userID)
    }

    fun addFriend(friend: Friend){
        repository.add(friend)
    }

    fun deleteFriend(friend: Friend){
        repository.delete(friend)
    }

    fun updateFriend(friendId : Int?, friend : Friend){
        repository.update(friendId, friend)
    }
}