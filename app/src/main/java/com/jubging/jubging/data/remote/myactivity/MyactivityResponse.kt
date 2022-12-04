package com.jubging.jubging.data.remote.myactivity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MyactivityResponse(
    @SerializedName("errorCode") val errorCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("created_at") val date: String,
    @SerializedName("step_cnt") val walk: String,
    @SerializedName("distance") val distance : String,
    @SerializedName("time") val time : String,
    @SerializedName("weight") val weight : String,
    @SerializedName("img_url") val imgUrl : String,
) : Serializable