package com.jubging.jubging.data.remote.picture

import com.mummoom.md.data.remote.auth.JubjubiResponse
import com.mummoom.md.data.remote.auth.PictureResponse
import com.mummoom.md.data.remote.auth.UserInfoResponse

interface PictureView{
    fun onPictureLoading()
    fun onPictureSuccess(pictureResponse: PictureResponse)
    fun onPictureFailure(errorCode: Int, message: String)
}