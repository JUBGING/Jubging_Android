package com.mummoom.md.data.remote.auth

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName


data class StartJubgingResponse(
    @SerializedName("errorCode") val errorCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("tongs_unlock_key") val jipgaeKey: String,
    )