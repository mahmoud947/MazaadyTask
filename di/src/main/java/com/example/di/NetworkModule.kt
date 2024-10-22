package com.example.di

import com.example.data.datasource.remote.CategoryService
import com.example.data.interceptor.AuthInterceptor
import com.example.data.interceptor.LanguageInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    val baseUrl: String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttp(
        authInterceptor: AuthInterceptor,
        languageInterceptor: LanguageInterceptor
    ): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient().newBuilder()
            .connectTimeout(15, TimeUnit.MINUTES)
            .readTimeout(15, TimeUnit.MINUTES)
            .writeTimeout(15, TimeUnit.MINUTES)
            .addInterceptor(authInterceptor)
            .addInterceptor(languageInterceptor)
            .addInterceptor(logger)
            .retryOnConnectionFailure(true)
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)

    @Provides
    @Singleton
    fun provideCategoryService(retrofit: Retrofit.Builder): CategoryService =
        retrofit.build().create(CategoryService::class.java)

}
