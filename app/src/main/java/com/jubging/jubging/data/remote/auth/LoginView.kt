package com.jubging.jubging.data.remote.auth


import com.mummoom.md.data.remote.auth.AuthResponse

interface LoginView{
    fun onLoginLoading()
    fun onLoginSuccess(authResponse: AuthResponse)
    fun onLoginFailure(errorCode: Int, message: String)
}