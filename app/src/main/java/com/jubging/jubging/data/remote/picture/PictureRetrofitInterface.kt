package com.jubging.jubging.data.remote.picture

import com.mummoom.md.data.remote.auth.JubjubiResponse
import com.mummoom.md.data.remote.auth.PictureResponse
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface PictureRetrofitInterface {
    @Multipart
    @POST("/api/v1/jubjubi/jubging-data/img")
    fun putPicture(
        @Part image:MultipartBody.Part,
    ): Call<PictureResponse>

}