package com.mummoom.md.data.remote.auth

import com.google.gson.annotations.SerializedName

//data class Auth (@SerializedName("accessToken") val accessToken: String)

data class UserInfoResponse(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("stepSum") val stepSum: Int,
    @SerializedName("distanceSum") val distanceSum: Float,
    @SerializedName("calorieSum") val calorieSum: Int,
    @SerializedName("totalPoints") val totalPoints: Int,
    )