package com.example.asteroidapp.ui.main

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.asteroidapp.api.RetrofitClient
import com.example.asteroidapp.db.AsteroidRoomDatabase
import com.example.asteroidapp.repository.NasaRepositoryImp

class MainViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {

            val repositoryImp = NasaRepositoryImp(
                AsteroidRoomDatabase.getInstance(context).asteroidsDao,
                RetrofitClient.api
            )

            MainViewModel(repositoryImp) as T
        } else {
            throw IllegalArgumentException("MainViewModel Class not found")
        }
    }
}