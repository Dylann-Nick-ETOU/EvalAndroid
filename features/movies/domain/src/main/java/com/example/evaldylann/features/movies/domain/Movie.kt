package com.example.evaldylann.features.movies.domain

/**
 * Un film du catalogue de ghivli.
 */
data class Movie(
    val id: String,
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