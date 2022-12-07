package com.jubging.jubging.data.remote.auth

import retrofit2.Call
import com.jubging.jubging.data.entities.User
import com.mummoom.md.data.remote.auth.AuthResponse
import com.mummoom.md.data.remote.auth.LogoutResponse
import retrofit2.Response
import retrofit2.http.*

interface AuthRetrofitInterface {
    @POST("/api/v1/auth/sign-up")
    fun signUp(@Body user: User): Call<SignUpResponse>

    @POST("/api/v1/auth/sign-in")
    fun login(@Body user: User): Call<AuthResponse>

    @POST("/api/v1/auth/logout")
    fun loggout(): Call<LogoutResponse>

}