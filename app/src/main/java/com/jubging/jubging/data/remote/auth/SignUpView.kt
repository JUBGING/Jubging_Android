package com.jubging.jubging.data.remote.auth

interface SignUpView {
    fun onSignUpLoading()
    fun onSignUpSuccess()
    fun onSignUpFailure(errorCode: Int, message: String)
}