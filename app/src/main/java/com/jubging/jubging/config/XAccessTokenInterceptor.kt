package com.jubging.jubging.config

import com.jubging.jubging.ApplicationClass

import com.jubging.jubging.utils.getJwt
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

//일일이 토큰 헤더를 붙이면 코드 중복 & 관리 번거롭
//okHttp3 인터셉터 활용 -> 모든 api 요청에 자동으로 토큰 세팅

class AuthorizationTokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        //디바이스에 jwt가 있을 때 가져옴
        val jwtToken: String? = getJwt()

        //jwt가 null이 아닐때 헤더에 넣어줌
        jwtToken?.let{
            builder.addHeader(ApplicationClass.Authorization, jwtToken)
        }

        return chain.proceed(builder.build())
    }
}