package com.jubging.jubging.data.remote.picture

import com.mummoom.md.data.remote.auth.JubjubiResponse
import com.mummoom.md.data.remote.auth.PictureResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface PictureRetrofitInterface {
    @Multipart
    @PATCH("/api/v1/jubjubi/jubjubi/jubging-data/img")
    fun putPicture(
        @Part image:MultipartBody.Part
    ): Call<PictureResponse>

}