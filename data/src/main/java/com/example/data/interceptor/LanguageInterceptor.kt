package com.example.data.interceptor;

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Invocation
import javax.inject.Inject

class LanguageInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val invocation = chain.request().tag(Invocation::class.java)
            ?: return chain.proceed(chain.request())
         return   chain.proceed(
                chain.request()
                    .newBuilder().also { builder: Request.Builder ->
                        builder.addHeader("Accept-Language", "en")
                    }
                    .build()
            )

    }
}
