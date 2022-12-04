package com.jubging.jubging.data.remote.myactivity

import com.mummoom.md.data.remote.auth.JubjubiResponse
import com.mummoom.md.data.remote.auth.UserInfoResponse

interface MyactivityView{
    fun onMyactivityLoading()
    fun onMyactivitySuccess(myactivityResponse: ArrayList<MyactivityResponse>)
    fun onMyactivityFailure(errorCode: Int, message: String)
}