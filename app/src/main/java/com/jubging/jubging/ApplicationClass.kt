package com.jubging.jubging;

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.jubging.jubging.config.XAccessTokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {
    companion object{
        const val X_AUTH_TOKEN: String = "X-AUTH-TOKEN"         // JWT Token Key
        const val TAG: String = "TEMPLATE-APP"                      // Log, SharedPreference
        const val APP_DATABASE = "$TAG-DB"

        //const val DEV_URL: String = "http://52.78.124.45:8080/";       // 테스트 서버 주소
        //const val PROD_URL: String = "https://api.template.com/"    // 실서버 주소
        //const val BASE_URL: String = DEV_URL

        lateinit var mSharedPreferences: SharedPreferences
        lateinit var retrofit: Retrofit
    }

    override fun onCreate() {
        super.onCreate()

        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(30000, TimeUnit.MILLISECONDS)
            .connectTimeout(30000, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
            .build()

//        retrofit = Retrofit.Builder() //한번생성하면 모든 API에서 사용하니까 한번로그인하면 무조건 헤더에 jwt가 들어감
//            .baseUrl(BASE_URL)
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()

        mSharedPreferences = applicationContext.getSharedPreferences(TAG, Context.MODE_PRIVATE)
    }
}