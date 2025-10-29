package com.example.evaldylann.features.movies.data.local


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 2)
abstract class MovieDb : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
