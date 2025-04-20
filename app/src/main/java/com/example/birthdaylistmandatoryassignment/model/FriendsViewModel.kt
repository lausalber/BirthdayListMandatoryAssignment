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

    fun getMyFriends(){
        repository.getMyFriends()
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

    fun sortFriendsByName(ascending: Boolean){
        repository.sortByName(ascending)
    }

    fun sortFriendsByAge(ascending: Boolean){
        repository.sortByAge(ascending)
    }

    fun sortFriendsByBirthday(ascending: Boolean){
        repository.sortByBirthday(ascending)
    }

    fun filterByName(name: String){
        repository.filterByName(name)
    }
     fun filterByAge(age: Int){
         repository.filterByAge(age)
     }
}