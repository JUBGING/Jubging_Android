package com.jubging.jubging.data.remote.userInfo

import com.jubging.jubging.ApplicationClass.Companion.retrofit
import com.jubging.jubging.data.remote.ErrorResponse
import com.mummoom.md.data.remote.auth.UserInfoResponse
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserInfoService {

    private lateinit var getUserInfoView: GetUserInfoView

    fun setGetUserInfoView(newView: GetUserInfoView){
        getUserInfoView = newView
    }

    fun getUserInfo(){
        val userInfoService = retrofit.create(UserInfoRetrofitInterface::class.java)

        userInfoService.getUserInfo().enqueue(object : Callback<UserInfoResponse> {
            override fun onResponse(call: Call<UserInfoResponse>, response: Response<UserInfoResponse>) {

                if(response.code() == 200){
                    val resp = response.body()!!
;                    getUserInfoView.onGetUserInfoSuccess(resp)
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
                        getUserInfoView.onGetUserInfoFailure(errorDto.errorCode, errorDto.message)
                    }
                }

            }

            override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                //Log.d("온페일러",t.message.toString())
                getUserInfoView.onGetUserInfoFailure(401,"네트워크 오류가 발생했습니다.")
            }
        })
    }


}