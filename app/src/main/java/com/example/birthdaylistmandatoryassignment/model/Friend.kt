package com.example.birthdaylistmandatoryassignment.model

data class Friend(
    val id: Int,
    val userId : String,
    val name : String,
    val birthYear : Int,
    val birthMonth : Int,
    val birthDayOfMonth : Int,
    val age : Int )