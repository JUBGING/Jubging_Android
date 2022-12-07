package com.jubging.jubging.data.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Jubging(
    @SerializedName("tongs_id") val tongs_id: Int,
    @SerializedName("jubjubi_id") val jubjubi_id: Int,
    @SerializedName("weight") val weight: Float,
    @SerializedName("calorie") val calorie: Int,
    @SerializedName("distance") val distance: Float,
    @SerializedName("step_cnt") val stepCnt: Int,
    @SerializedName("tongs_return") val tongsReturn: Boolean,
) : Serializable