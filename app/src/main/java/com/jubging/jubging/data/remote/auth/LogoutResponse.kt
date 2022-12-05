package com.mummoom.md.data.remote.auth

import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @SerializedName("errorCode") val errorCode: Int,
    @SerializedName("message") val message: String,
    )