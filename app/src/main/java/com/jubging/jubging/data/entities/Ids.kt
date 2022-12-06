package com.jubging.jubging.data.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Ids(
    @SerializedName("tongs_id") val tongs_id: Int,
    @SerializedName("jubjubi_id") val jubjubi_id: Int,
) : Serializable