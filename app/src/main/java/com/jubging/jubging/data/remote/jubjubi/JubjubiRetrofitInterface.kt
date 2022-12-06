package com.jubging.jubging.data.remote.jubjubi

import com.jubging.jubging.data.entities.Ids
import com.mummoom.md.data.remote.auth.JubjubiResponse
import com.mummoom.md.data.remote.auth.StartJubgingResponse
import retrofit2.Call
import retrofit2.http.*


interface JubjubiRetrofitInterface {

    @GET("/api/v1/jubjubi/info/{userPosition}")
    fun getJubjubiInfo(
        @Path("userPosition")userPosition: String
    ): Call<List<JubjubiResponse>>

    @POST("/api/v1/jubjubi/jubging-data")
    fun startJubging(
        @Body ids: Ids
    ):Call<StartJubgingResponse>

    @PATCH("/api/v1/jubjubi/jubging-data/tongs")
    fun finishJubging(

    )

}