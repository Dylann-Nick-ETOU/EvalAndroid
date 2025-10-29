package com.example.evaldylann.features.movies.domain

/**
 * Le contrat de source de données (Movie)
 */
interface MovieRepository {
    /**
     *
     */
    suspend fun getMovies(forceRefresh: Boolean = false): Result<List<Movie>>

    /**
     *@param id identifiant du film
     * @return film correspondant ou `null` s’il n’existe pas
     */
    suspend fun getMovie(id: String): Movie
}