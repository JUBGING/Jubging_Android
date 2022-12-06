package com.jubging.jubging.data.remote.picture

import android.util.Log
import com.jubging.jubging.ApplicationClass.Companion.retrofit
import com.jubging.jubging.data.remote.ErrorResponse
import com.mummoom.md.data.remote.auth.PictureResponse
import okhttp3.MultipartBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object PictureService {

//뭘받아서 넣어줄지 uri 부분에
    fun sendUri(pictureView:PictureView,uri:MultipartBody.Part){
    Log.d("카메라서비스",uri.toString())
        val pictureService = retrofit.create(PictureRetrofitInterface::class.java)

        pictureView.onPictureLoading()

        pictureService.putPicture(uri).enqueue(object : Callback<PictureResponse> {
            override fun onResponse(call: Call<PictureResponse>, response: Response<PictureResponse>) {
                Log.d("카메라uri",uri.toString())
                if(response.code() == 200){
                    //Log.d("줍줍정상",response.toString())
                    val resp = response.body()!!
;                    pictureView.onPictureSuccess(resp)
                }
                else{
                    //Log.d("줍줍에러",response.errorBody().toString())
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