package com.example.asteroidapp.api

import com.example.asteroidapp.models.PictureOfDay
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApiService {

    @GET("neo/rest/v1/feed")
    suspend fun getAllAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
    ): String

    @GET("planetary/apod")
    suspend fun getAsteroidsImage():PictureOfDay



}

