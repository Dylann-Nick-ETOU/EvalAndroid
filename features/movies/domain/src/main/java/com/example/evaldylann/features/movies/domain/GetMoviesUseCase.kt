package com.example.evaldylann.features.movies.domain

/**
 *
 */
class GetMoviesUseCase(private val repo: MovieRepository) {
    suspend operator fun invoke(forceRefresh: Boolean = false) = repo.getMovies(forceRefresh)
}