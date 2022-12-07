package com.jubging.jubging.data.remote.auth

import android.content.Intent
import android.util.Log
import com.jubging.jubging.ApplicationClass.Companion.retrofit
import com.jubging.jubging.data.remote.ErrorResponse
import com.jubging.jubging.data.remote.userInfo.GetUserInfoView
import com.jubging.jubging.data.remote.userInfo.UserInfoRetrofitInterface
import com.jubging.jubging.ui.login.LoginActivity
import com.mummoom.md.data.remote.auth.LogoutResponse
import com.mummoom.md.data.remote.auth.UserInfoResponse
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LogoutService {

    private lateinit var logoutView: LogoutView

    fun setLogoutView(newView: LogoutView){
        logoutView = newView
    }


    fun logout(){
        val authService = retrofit.create(AuthRetrofitInterface::class.java)

        authService.loggout().enqueue(object: Callback<LogoutResponse>{
            override fun onResponse(call: Call<LogoutResponse>, response: Response<LogoutResponse>) {
                Log.d("로그아웃 리스폰스",response.toString())
                if(response.code() == 200){
                    val resp = response.body()!!
                    logoutView.onLogoutSuccess(resp)
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
                        logoutView.onLogoutFailure(errorDto.errorCode, errorDto.message)
                    }
                }
            }

            override fun onFailure(call: Call<LogoutResponse>, t: Throwable) {
                Log.d("로그아웃 실패",t.toString())
                logoutView.onLogoutFailure(401,"네트워크 오류가 발생했습니다.")
            }

        })
    }


}