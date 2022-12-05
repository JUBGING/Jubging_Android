package com.jubging.jubging.data.remote.auth


import com.mummoom.md.data.remote.auth.LogoutResponse

interface LogoutView{
    fun onLogoutLoading()
    fun onLogoutSuccess(logoutResponse: LogoutResponse)
    fun onLogoutFailure(errorCode: Int, message: String)
}