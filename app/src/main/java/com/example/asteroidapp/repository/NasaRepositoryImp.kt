package com.example.asteroidapp.repository

import androidx.lifecycle.LiveData
import com.example.asteroidapp.api.NasaApiService
import com.example.asteroidapp.api.getNextSevenDaysFormattedDates
import com.example.asteroidapp.api.parseAsteroidsJsonResult
import com.example.asteroidapp.db.AsteroidDao
import com.example.asteroidapp.models.Asteroid
import com.example.asteroidapp.models.PictureOfDay
import org.json.JSONObject

class NasaRepositoryImp(
    private val asteroidDao: AsteroidDao,
    private val nasaApiService: NasaApiService
) : NasaRepository {

    override suspend fun getAsteroidsFromNetwork(): List<Asteroid> {
        val dates: List<String> = getNextSevenDaysFormattedDates()
        val startDate = dates.first()
        val endDate = dates.last()

        return parseAsteroidsJsonResult(
            JSONObject(
                nasaApiService.getAllAsteroids(startDate, endDate)
            )
        )
    }


    override suspend fun getAsteroidsImageFromApi(): PictureOfDay {
         return nasaApiService.getAsteroidsImage()
    }

    override suspend fun insertAsteroids(list: List<Asteroid>) {
        asteroidDao.insert(list)
    }

    override fun getTodayAsteroids(): LiveData<List<Asteroid>> {
      return  asteroidDao.getTodayAsteroids()
    }

    override fun getWeeklyAsteroids(): LiveData<List<Asteroid>> {
     return asteroidDao.getWeeklyAsteroids()
    }

    override fun getAllAsteroidsfromDB(): LiveData<List<Asteroid>> {
       return asteroidDao.getAllAsteroids()
    }
}