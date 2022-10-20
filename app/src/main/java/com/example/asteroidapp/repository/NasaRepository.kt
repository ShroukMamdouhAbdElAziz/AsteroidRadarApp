package com.example.asteroidapp.repository

import androidx.lifecycle.LiveData
import com.example.asteroidapp.models.Asteroid
import com.example.asteroidapp.models.PictureOfDay

interface NasaRepository {

 suspend fun getAsteroidsFromNetwork():List<Asteroid>

 suspend fun getAsteroidsImageFromApi(): PictureOfDay

 suspend fun insertAsteroids(list:List<Asteroid>)

 fun getTodayAsteroids():LiveData<List<Asteroid>>
 fun getWeeklyAsteroids():LiveData<List<Asteroid>>
 fun getAllAsteroidsfromDB(): LiveData<List<Asteroid>>

}