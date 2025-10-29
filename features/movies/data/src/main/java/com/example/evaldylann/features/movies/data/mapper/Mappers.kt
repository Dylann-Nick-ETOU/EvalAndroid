package com.example.evaldylann.features.movies.data.mapper


import com.example.evaldylann.features.movies.data.dto.GhibliMovieDto
import com.example.evaldylann.features.movies.data.local.MovieEntity
import com.example.evaldylann.features.movies.domain.Movie

// ---- DTO -> Entity ----
fun GhibliMovieDto.toEntity() = MovieEntity(
    id = id,
    title = title,
    description = description,
    director = director,
    producer = producer,
    releaseYear = releaseDate,
    runtimeMinutes = runningTime.toIntOrNull() ?: 0,
    score = rtScore.toIntOrNull() ?: 0,
    posterUrl = image,
    bannerUrl = movie_banner
)

// ---- Entity -> Domain ----
fun MovieEntity.toDomain() = Movie(
    id = id,
    title = title,
    description = description,
    director = director,
    producer = producer,
    releaseYear = releaseYear,
    runtimeMinutes = runtimeMinutes,
    score = score,
    posterUrl = posterUrl,
    bannerUrl = bannerUrl
)

// ---- DTO -> Domain (pour appels réseau sans cache) ----
fun GhibliMovieDto.toDomain() = Movie(
    id = id,
    title = title,
    description = description,
    director = director,
    producer = producer,
    releaseYear = releaseDate,
    runtimeMinutes = runningTime.toIntOrNull() ?: 0,
    score = rtScore.toIntOrNull() ?: 0,
    posterUrl = image,
    bannerUrl = movie_banner
)


