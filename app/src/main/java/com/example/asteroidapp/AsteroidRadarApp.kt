package com.example.asteroidapp

import android.app.Application
import android.util.Log
import androidx.work.*
import com.example.asteroidapp.work.NasaWorker
import com.example.asteroidapp.work.NasaWorker.Companion.WORKER_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidRadarApp : Application() {

   private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        initApp()
    }

   private fun initApp() {
        applicationScope.launch {
            startWorker()
        }
    }

   private fun startWorker() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<NasaWorker>(24, TimeUnit.HOURS)
            .setConstraints(constraints).build()

        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(WORKER_NAME, ExistingPeriodicWorkPolicy.KEEP, workRequest)




    }
}