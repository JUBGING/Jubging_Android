package com.jubging.jubging.data.remote.jubjubi

import com.mummoom.md.data.remote.auth.JubjubiResponse
import retrofit2.Call
import com.mummoom.md.data.remote.auth.UserInfoResponse
import retrofit2.http.*

interface JubjubiRetrofitInterface {

    @GET("/api/v1/jubjubi/info/{userPosition}")
    fun getUserPosition(
        @Path("userPosition")userPosition: String
    ): Call<JubjubiResponse>

}