package com.jubging.jubging.data.remote.jubjubi

import android.content.Context
import com.jubging.jubging.ApplicationClass.Companion.retrofit
import com.jubging.jubging.data.remote.ErrorResponse
import com.jubging.jubging.data.remote.userInfo.UserInfoRetrofitInterface
import com.jubging.jubging.data.remote.userInfo.GetUserInfoView
import com.mummoom.md.data.remote.auth.JubjubiResponse
import com.mummoom.md.data.remote.auth.UserInfoResponse
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class JubjubiService {

    fun getUserPosition(jubjubiView: JubjubiView,userPosition: String){
        val jubjubiService = retrofit.create(JubjubiRetrofitInterface::class.java)

        jubjubiService.getUserPosition(userPosition).enqueue(object : Callback<JubjubiResponse> {
            override fun onResponse(call: Call<JubjubiResponse>, response: Response<JubjubiResponse>) {

                if(response.code() == 200){
                    val resp = response.body()!!
;                    jubjubiView.onJubjubiSuccess(resp)
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
                        jubjubiView.onJubjubiFailure(errorDto.errorCode, errorDto.message)
                    }
                }

            }

            override fun onFailure(call: Call<JubjubiResponse>, t: Throwable) {
                jubjubiView.onJubjubiFailure(401,"네트워크 오류가 발생했습니다.")
            }


        })
    }


}