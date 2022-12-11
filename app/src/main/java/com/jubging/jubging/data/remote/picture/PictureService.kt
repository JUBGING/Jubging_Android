package com.jubging.jubging.data.remote.picture

import android.util.Log
import com.jubging.jubging.ApplicationClass.Companion.retrofit
import com.jubging.jubging.data.remote.ErrorResponse
import com.mummoom.md.data.remote.auth.PictureResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object PictureService {

    fun sendUri(pictureView:PictureView,uri: MultipartBody.Part,weight: RequestBody){
    Log.d("카메라서비스 uri",uri.toString())
    Log.d("카메라서비스 weight",weight.toString())
        val pictureService = retrofit.create(PictureRetrofitInterface::class.java)

        pictureView.onPictureLoading()

        pictureService.putPicture(uri,weight).enqueue(object : Callback<PictureResponse> {
            override fun onResponse(call: Call<PictureResponse>, response: Response<PictureResponse>) {
                Log.d("카메라uri",uri.toString())
                //Log.d("카메라 오류",response.errorBody()!!.string())
                if(response.code() == 200){
                    val resp = response.body()!!
;                    pictureView.onPictureSuccess(resp)
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
                        pictureView.onPictureFailure(errorDto.errorCode, errorDto.message)
                    }
                }

            }

            override fun onFailure(call: Call<PictureResponse>, t: Throwable) {
                Log.d("카메라패일",t.message.toString())
                pictureView.onPictureFailure(401,"네트워크 오류가 발생했습니다.")
            }
        })
    }


}