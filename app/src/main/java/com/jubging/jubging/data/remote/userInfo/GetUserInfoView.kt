package com.jubging.jubging.data.remote.userInfo

import com.mummoom.md.data.remote.auth.UserInfoResponse

interface GetUserInfoView{
    fun onGetUserInfoLoading()
    fun onGetUserInfoSuccess(userInfoResponse: UserInfoResponse)
    fun onGetUserInfoFailure(errorCode: Int, message: String)
}