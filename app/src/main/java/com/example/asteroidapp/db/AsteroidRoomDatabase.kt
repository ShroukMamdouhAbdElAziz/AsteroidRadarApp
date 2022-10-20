package com.example.asteroidapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.asteroidapp.models.Asteroid


@Database(entities = [Asteroid::class], version = 1, exportSchema = false)
abstract class AsteroidRoomDatabase : RoomDatabase() {

    abstract val asteroidsDao: AsteroidDao

    companion object {

        @Volatile
        private var INSTANCE: AsteroidRoomDatabase? = null

        fun getInstance(context: Context): AsteroidRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidRoomDatabase::class.java,
                        "asteroids_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
