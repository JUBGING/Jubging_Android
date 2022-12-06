package com.jubging.jubging.data.remote.jubjubi

import com.mummoom.md.data.remote.auth.JubjubiResponse
import com.mummoom.md.data.remote.auth.StartJubgingResponse
import com.mummoom.md.data.remote.auth.UserInfoResponse

interface StartJubgingView{
    fun onStartJubgingLoading()
    fun onStartJubgingSuccess(startJubgingResponse: StartJubgingResponse)
    fun onStartJubgingFailure(errorCode: Int, message: String)
}