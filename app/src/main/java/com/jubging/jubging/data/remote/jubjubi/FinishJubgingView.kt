package com.jubging.jubging.data.remote.jubjubi

import com.mummoom.md.data.remote.auth.FinishJubgingResponse
import com.mummoom.md.data.remote.auth.JubjubiResponse
import com.mummoom.md.data.remote.auth.StartJubgingResponse
import com.mummoom.md.data.remote.auth.UserInfoResponse

interface FinishJubgingView{
    fun onFinishJubgingLoading()
    fun onFinishJubgingSuccess(finishJubgingResponse: FinishJubgingResponse)
    fun onFinishJubgingFailure(errorCode: Int, message: String)
}