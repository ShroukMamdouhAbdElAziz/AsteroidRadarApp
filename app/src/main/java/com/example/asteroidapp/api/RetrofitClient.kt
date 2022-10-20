package com.example.asteroidapp.api


import com.example.asteroidapp.BuildConfig
import com.example.asteroidapp.common.Constants.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


object RetrofitClient {

    private const val API_KEY = "api_key"

    private val retrofit by lazy {
        Retrofit.Builder().client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api:NasaApiService by lazy {
        retrofit.create(NasaApiService::class.java)
    }


    private val okHttpClient by lazy {

        OkHttpClient.Builder()
            .addInterceptor { apiKeyInterceptor(it) }
            .build()
    }


    private fun apiKeyInterceptor(it: Interceptor.Chain): Response {

        val originalRequest = it.request()
        val originalHttpUrl = originalRequest.url()

        val newHttpUrl = originalHttpUrl.newBuilder()
            .addQueryParameter(API_KEY, BuildConfig.API_KEY)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newHttpUrl)
            .build()

        return it.proceed(newRequest)
    }







}