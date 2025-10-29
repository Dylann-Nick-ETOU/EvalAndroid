package com.example.evaldylann.features.movies.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GhibliMovieDto(
    val id: String,
    val title: String,
    val original_title: String? = null,
    val image: String? = null,
    val movie_banner: String? = null,
    val description: String,
    val director: String,
    val producer: String,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("running_time") val runningTime: String,
    @SerialName("rt_score") val rtScore: String
)