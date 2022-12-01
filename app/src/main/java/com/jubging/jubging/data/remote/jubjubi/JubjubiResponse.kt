package com.mummoom.md.data.remote.auth

import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName


data class JubjubiResponse(
    @SerializedName("name") val name: Int,
    @SerializedName("plastic_bag_cnt") val plastic_bag_cnt: Int,
    @SerializedName("tongs_cnt") val tongs_cnt: Int,
    @SerializedName("lat") val lat: LatLng,
    @SerializedName("lng") val lng: LatLng,
    )