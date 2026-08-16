package com.example.userinfosender

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val name: String,
    val age: Int,
    val address: String,
    val gender: String,
    val country: String
) : Parcelable