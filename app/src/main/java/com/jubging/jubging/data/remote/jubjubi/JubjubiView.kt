package com.jubging.jubging.data.remote.jubjubi

import com.mummoom.md.data.remote.auth.JubjubiResponse
import com.mummoom.md.data.remote.auth.UserInfoResponse

interface JubjubiView{
    fun onJubjubiLoading()
    fun onJubjubiSuccess(jubjubiResponse: List<JubjubiResponse>)
    fun onJubjubiFailure(errorCode: Int, message: String)
}