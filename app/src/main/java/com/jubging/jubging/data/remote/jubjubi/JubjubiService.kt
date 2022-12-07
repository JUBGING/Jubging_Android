package com.jubging.jubging.data.remote.jubjubi

import android.util.Log
import com.jubging.jubging.ApplicationClass.Companion.retrofit
import com.jubging.jubging.data.entities.Ids
import com.jubging.jubging.data.entities.Jubging
import com.jubging.jubging.data.remote.ErrorResponse
import com.mummoom.md.data.remote.auth.FinishJubgingResponse
import com.mummoom.md.data.remote.auth.JubjubiResponse
import com.mummoom.md.data.remote.auth.StartJubgingResponse
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
                Log.d("줍줍api",response.toString())
                if(response.code() == 200){
                    Log.d("줍줍정상",response.toString())
                    val resp = response.body()!!
;                    jubjubiView.onJubjubiSuccess(resp)
                }
                else{
                    Log.d("줍줍에러",response.errorBody().toString())
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
                Log.d("박정훈박서준",call.toString())
                jubjubiView.onJubjubiFailure(401,"네트워크 오류가 발생했습니다.")
            }

        })
    }

    fun startJubging(startJubgingView:StartJubgingView, ids: Ids){
        Log.d("시작줍깅서비스",ids.toString())
        val startJubgingService = retrofit.create(JubjubiRetrofitInterface::class.java)

        startJubgingService.startJubging(ids).clone().enqueue(object : Callback<StartJubgingResponse>{
            override fun onResponse(call: Call<StartJubgingResponse>, response: Response<StartJubgingResponse>) {

                if(response.code() == 200){
                    val resp = response.body()!!
                    startJubgingView.onStartJubgingSuccess(resp)
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
                        startJubgingView.onStartJubgingFailure(errorDto.errorCode, errorDto.message)
                    }
                }
            }

            override fun onFailure(call: Call<StartJubgingResponse>, t: Throwable) {
                Log.d("시작줍깅api부르기실패",t.message.toString())
                startJubgingView.onStartJubgingFailure(401,"네트워크 오류가 발생했습니다.")
            }
        })
    }

    fun finishJubging(finishJubgingView: FinishJubgingView, jubging: Jubging){
        Log.d("끝줍깅서비스",jubging.toString())
        val finishJubgingService = retrofit.create(JubjubiRetrofitInterface::class.java)

        finishJubgingService.finishJubging(jubging).clone().enqueue(object : Callback<FinishJubgingResponse>{
            override fun onResponse(call: Call<FinishJubgingResponse>, response: Response<FinishJubgingResponse>) {

                if(response.code() == 200){
                    val resp = response.body()!!
                    finishJubgingView.onFinishJubgingSuccess(resp)
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
                        finishJubgingView.onFinishJubgingFailure(errorDto.errorCode, errorDto.message)
                    }
                }
            }

            override fun onFailure(call: Call<FinishJubgingResponse>, t: Throwable) {
                Log.d("끝api부르기실패",t.message.toString())
                finishJubgingView.onFinishJubgingFailure(401,"네트워크 오류가 발생했습니다.")
            }
        })
    }

}