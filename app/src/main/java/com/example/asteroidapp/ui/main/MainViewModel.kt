package com.example.asteroidapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asteroidapp.models.Asteroid
import com.example.asteroidapp.models.PictureOfDay
import com.example.asteroidapp.repository.NasaRepositoryImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repositoryImp: NasaRepositoryImp) : ViewModel() {

    // as livedata

    var asteroids: LiveData<List<Asteroid>> = MutableLiveData<List<Asteroid>>(emptyList())
        private set


    private val _filterOption = MutableLiveData<FilterOption>()
    val filterOption: LiveData<FilterOption>
        get() = _filterOption


    private val _dayImage = MutableLiveData<PictureOfDay>()
    val dayImage: LiveData<PictureOfDay>
        get() = _dayImage



    init {

        filterAsteroids(FilterOption.ALL)
        viewModelScope.launch(Dispatchers.IO) {
            _dayImage.postValue(repositoryImp.getAsteroidsImageFromApi())


        }
    }



    enum class FilterOption {
        TODAY,
        WEEKLY,
        ALL,
    }

    fun filterAsteroids(filterOption: FilterOption) {
        asteroids = when (filterOption) {
            FilterOption.TODAY -> repositoryImp.getTodayAsteroids()
            FilterOption.WEEKLY -> repositoryImp.getWeeklyAsteroids()
            FilterOption.ALL -> repositoryImp.getAllAsteroidsfromDB()

        }


        // to update MutableliveData
        _filterOption.value = filterOption


    }
}