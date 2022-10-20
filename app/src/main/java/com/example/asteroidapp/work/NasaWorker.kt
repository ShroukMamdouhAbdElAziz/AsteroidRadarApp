package com.example.asteroidapp.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.asteroidapp.api.RetrofitClient
import com.example.asteroidapp.db.AsteroidRoomDatabase
import com.example.asteroidapp.models.Asteroid
import com.example.asteroidapp.repository.NasaRepositoryImp

class NasaWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    lateinit var repositoryImp :NasaRepositoryImp

    override suspend fun doWork(): Result {



        repositoryImp = NasaRepositoryImp( AsteroidRoomDatabase.getInstance(applicationContext).asteroidsDao ,RetrofitClient.api)
        try {
           // applicationContext for database to be aware with app lifecycle

            // TODO: fetch asteroid from nasa Api then save it in room
           val asteroidList :List<Asteroid> = repositoryImp.getAsteroidsFromNetwork()
            repositoryImp.insertAsteroids(asteroidList)

        }catch (e:Exception){
            // to print the  exception details
            e.printStackTrace()
            return Result.retry()
        }

        return  Result.success()

    }

    companion object{
        // to return the class Name
        val WORKER_NAME:String = NasaWorker::class.java.simpleName
    }
}