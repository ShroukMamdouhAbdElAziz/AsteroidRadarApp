package com.example.asteroidapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.asteroidapp.models.Asteroid

@Dao
interface AsteroidDao {
    // TODO : insert , Get today asteroids , get Weekly asteroids , get all asteroids all return liveData
    @Insert
    suspend fun insert(asteroid: List<Asteroid>)

    // get all asteroids

    @Query("SELECT * FROM asteroids_Data_table ORDER BY id DESC")
    fun getAllAsteroids(): LiveData<List<Asteroid>>

    // get today asteroids
    @Query("SELECT * FROM asteroids_Data_table WHERE closeApproachDate = date() ORDER BY id DESC ")
     fun getTodayAsteroids(): LiveData<List<Asteroid>>

    // get weekly asteroids
    @Query("SELECT * FROM asteroids_Data_table Where closeApproachDate BETWEEN date() and date('now','+7 days') ORDER BY id DESC LIMIT 7")
     fun getWeeklyAsteroids(): LiveData<List<Asteroid>>
}