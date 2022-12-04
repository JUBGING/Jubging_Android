package com.mummoom.md.data.remote.auth

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName


data class JubjubiResponse(
    @SerializedName("errorCode") val errorCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("name") val name: String,
    @SerializedName("plastic_bag_cnt") val plastic_bag_cnt: Int,
    @SerializedName("tongs_cnt") val tongs_cnt: Int,
    @SerializedName("jubjubi_id") val jubjubi_id: Int,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,

    )