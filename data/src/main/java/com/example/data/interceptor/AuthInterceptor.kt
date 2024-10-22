package com.example.data.interceptor

import com.example.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Invocation
import javax.inject.Inject;

class AuthInterceptor @Inject constructor() : Interceptor {
    val apiPrivateKey: String = BuildConfig.API_PRIVATE_KEY
    override fun intercept(chain: Interceptor.Chain): Response {
        val invocation = chain.request().tag(Invocation::class.java)
            ?: return chain.proceed(chain.request())
         return   chain.proceed(
                chain.request()
                    .newBuilder().also { builder: Request.Builder ->
                        builder.addHeader("private-key", apiPrivateKey)
                    }
                    .build()
            )
    }
}
