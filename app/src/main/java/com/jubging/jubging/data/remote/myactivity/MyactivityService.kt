package com.jubging.jubging.data.remote.myactivity

import android.util.Log
import com.jubging.jubging.ApplicationClass.Companion.retrofit
import com.jubging.jubging.data.remote.ErrorResponse
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyactivityService {

    private lateinit var myactivityView: MyactivityView

    fun setMyactivityView(newView: MyactivityView){
        myactivityView = newView
    }

    fun getMyactivity(){
        val myactivityService = retrofit.create(MyactivityRetrofitInterface::class.java)

        myactivityService.getMyactivity().enqueue(object : Callback<ArrayList<MyactivityResponse>> {
            override fun onResponse(call: Call<ArrayList<MyactivityResponse>>, response: Response<ArrayList<MyactivityResponse>>) {
                Log.d("활동들어오나",response.toString())
                if(response.code() == 200){
                    val resp = response.body()!!
                    myactivityView.onMyactivitySuccess(resp)
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
                        myactivityView.onMyactivityFailure(errorDto.errorCode, errorDto.message)
                    }
                }

            }

            override fun onFailure(call: Call<ArrayList<MyactivityResponse>>, t: Throwable) {
                myactivityView.onMyactivityFailure(401,"네트워크 오류가 발생했습니다.")
            }
        })
    }


}