package com.jubging.jubging;

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.jubging.jubging.config.AuthorizationTokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {
    companion object{
        const val Authorization: String = "Authorization"         // JWT Token Key -> Bearer + accessToken
        const val TAG: String = "TEMPLATE-APP"                      // Log, SharedPreference
        const val APP_DATABASE = "$TAG-DB"

        const val DEV_URL: String = "http://43.200.116.118:8080/";       // 테스트 서버 주소
        //const val PROD_URL: String = "https://api.template.com/"    // 실서버 주소
        const val BASE_URL: String = DEV_URL

        lateinit var mSharedPreferences: SharedPreferences
        lateinit var retrofit: Retrofit

        var photo_uri: String = ""
    }


    override fun onCreate() {
        super.onCreate()

    //request 서버로 요청할 클리이언트
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(30000, TimeUnit.MILLISECONDS)
            .connectTimeout(30000, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(AuthorizationTokenInterceptor()) // JWT 자동 헤더 전송
            .build()

        retrofit = Retrofit.Builder() //한번생성하면 모든 API에서 사용하니까 한번로그인하면 무조건 헤더에 jwt가 들어감
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //MODE_PRIVATE은 SharedPreferences의 접근 권한같은 개념
        //MODE_PRIVATE => 생성한 application에서만 사용 가능 -> 자기 앱에서만 접근 가능하다
        mSharedPreferences = applicationContext.getSharedPreferences(TAG, Context.MODE_PRIVATE)
    }


}