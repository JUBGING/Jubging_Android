package com.jubging.jubging.config

import com.jubging.jubging.ApplicationClass

import com.jubging.jubging.utils.getJwt
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class XAccessTokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        val jwtToken: String? = getJwt() //디바이스에 jwt가 있을 때 가져옴

        jwtToken?.let{
            builder.addHeader(ApplicationClass.X_AUTH_TOKEN, jwtToken) //null이 아닐때 헤더에 넣어줌
        }

        return chain.proceed(builder.build())
    }
}