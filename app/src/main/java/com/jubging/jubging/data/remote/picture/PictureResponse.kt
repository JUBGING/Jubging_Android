package com.mummoom.md.data.remote.auth

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName


data class PictureResponse(
    @SerializedName("errorCode") val errorCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("url") val url: String,
    )