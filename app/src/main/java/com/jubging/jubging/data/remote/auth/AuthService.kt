package com.jubging.jubging.data.remote.auth

import android.util.Log
import com.jubging.jubging.ApplicationClass.Companion.retrofit
import com.jubging.jubging.data.entities.User
import com.jubging.jubging.data.remote.ErrorResponse
import com.mummoom.md.data.remote.auth.AuthResponse
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object AuthService {

    fun signUp(signUpView: SignUpView, user: User){
        val authService = retrofit.create(AuthRetrofitInterface::class.java)

        signUpView.onSignUpLoading()

        authService.signUp(user).enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {

                if(response.code() == 201){
                    val resp = response.body()!!
                    signUpView.onSignUpSuccess()
                }
                else{
                    var jsonObject: JSONObject? = null
                    var errorDto : ErrorResponse? = null
                    try{
                        jsonObject = JSONObject(response.errorBody()!!.string())
                        val errorCode = jsonObject.getInt("errorCode")
                        val message = jsonObject.getString("message")
                        errorDto = ErrorResponse(errorCode= errorCode, message)
                    }
                    catch (e: JSONException){
                        e.printStackTrace()
                    }

                    if (errorDto != null) {
                        signUpView.onSignUpFailure(errorDto.errorCode, errorDto.message)
                    }
                }

            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                Log.d("온페일러",t.message.toString())
                signUpView.onSignUpFailure(401,"네트워크 오류가 발생했습니다.")
            }
        })
    }

    fun login(loginView: LoginView, user: User){
        val authService = retrofit.create(AuthRetrofitInterface::class.java)

        loginView.onLoginLoading()

        authService.login(user).clone().enqueue(object : Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("로그인", user.toString())

                if(response.code() == 200){
                    val resp = response.body()!!
                    loginView.onLoginSuccess(resp)
                }
                else{
                    var jsonObject: JSONObject? = null
                    var errorDto : ErrorResponse? = null
                    try{
                        jsonObject = JSONObject(response.errorBody()!!.string())
                        val errorCode = jsonObject.getInt("errorCode")
                        val message = jsonObject.getString("message")
                        errorDto = ErrorResponse(errorCode= errorCode, message)
                    }
                    catch (e: JSONException){
                        e.printStackTrace()
                    }

                    if (errorDto != null) {
                        loginView.onLoginFailure(errorDto.errorCode, errorDto.message)
                    }
                }
         }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("온페일러",t.message.toString())
                loginView.onLoginFailure(401,"네트워크 오류가 발생했습니다.")
            }
        })
    }
}