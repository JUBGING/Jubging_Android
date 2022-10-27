package com.jubging.jubging.data.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("password") val password : String,
) : Serializable
