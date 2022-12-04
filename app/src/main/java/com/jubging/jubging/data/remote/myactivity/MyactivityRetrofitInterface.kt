package com.jubging.jubging.data.remote.myactivity

import com.mummoom.md.data.remote.auth.JubjubiResponse
import retrofit2.Call
import retrofit2.http.*


interface MyactivityRetrofitInterface {

    @GET("/api/v1/users/jubging_data")
    fun getMyactivity(): Call<ArrayList<MyactivityResponse>>

}