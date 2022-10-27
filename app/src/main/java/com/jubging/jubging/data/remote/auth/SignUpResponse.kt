package com.jubging.jubging.data.remote.auth

import com.google.gson.annotations.SerializedName


data class SignUpResponse (
    @SerializedName("errorCode") val errorCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("id") val id: String,
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
)