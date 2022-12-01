package com.jubging.jubging.data.remote.jubjubi

import android.util.Log
import com.jubging.jubging.ApplicationClass.Companion.retrofit
import com.jubging.jubging.data.remote.ErrorResponse
import com.mummoom.md.data.remote.auth.JubjubiResponse
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class JubjubiService {


    private lateinit var jubjubiView: JubjubiView

    fun setJubjubiView(newView: JubjubiView){
        jubjubiView = newView
    }

    fun getJubjubiInfo(userPosition: String){
        val jubjubiService = retrofit.create(JubjubiRetrofitInterface::class.java)
        Log.d("줍줍이들어오나",userPosition)

        jubjubiService.getJubjubiInfo(userPosition).enqueue(object : Callback<List<JubjubiResponse>> {
            override fun onResponse(call: Call<List<JubjubiResponse>>, response: Response<List<JubjubiResponse>>) {

                if(response.code() == 200){
                    val resp = response.body()!!
                    Log.d("줍줍1",resp.toString())
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

            override fun onFailure(call: Call<List<JubjubiResponse>>, t: Throwable) {
                jubjubiView.onJubjubiFailure(401,"네트워크 오류가 발생했습니다.")
            }


        })
    }


}