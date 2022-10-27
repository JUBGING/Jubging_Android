package com.jubging.jubging.ui.signUp

interface SignUpView {
    fun onSignUpLoading()
    fun onSignUpSuccess()
    fun onSignUpFailure(errorCode: Int, message: String)
}