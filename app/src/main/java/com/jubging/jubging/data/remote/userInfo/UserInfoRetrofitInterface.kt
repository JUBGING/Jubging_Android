package com.jubging.jubging.data.remote.userInfo

import retrofit2.Call
import com.mummoom.md.data.remote.auth.UserInfoResponse
import retrofit2.http.*

interface UserInfoRetrofitInterface {
    @GET("/api/v1/users")
    fun getUserInfo(): Call<UserInfoResponse>

}