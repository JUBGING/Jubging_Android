package com.mummoom.md.data.remote.auth

import com.google.gson.annotations.SerializedName

data class Auth (@SerializedName("accessToken") val accessToken: String)

data class AuthResponse(
    @SerializedName("errorCode") val errorCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("grantType") val grantType: String,
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String,
    )