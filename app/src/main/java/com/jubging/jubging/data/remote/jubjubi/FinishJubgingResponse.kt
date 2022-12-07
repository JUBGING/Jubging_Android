package com.mummoom.md.data.remote.auth

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName


data class FinishJubgingResponse(
    @SerializedName("errorCode") val errorCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("weight") val weight: Float,
    @SerializedName("calorie") val calorie: Int,
    @SerializedName("distance") val distance: Float,
    @SerializedName("step_cnt") val stepCnt: Int,
    @SerializedName("tongs_return") val tongsReturn: Boolean,

    )