package com.example.evaldylann.features.movies.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val director: String,
    val producer: String,
    val releaseYear: String,
    val runtimeMinutes: Int,
    val score: Int,
    val posterUrl: String?,
    val bannerUrl: String?
)
