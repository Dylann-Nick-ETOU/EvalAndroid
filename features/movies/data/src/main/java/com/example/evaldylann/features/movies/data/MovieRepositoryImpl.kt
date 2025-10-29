package com.example.evaldylann.features.movies.data

import com.example.evaldylann.features.movies.data.local.MovieDao
import com.example.evaldylann.features.movies.data.mapper.toDomain
import com.example.evaldylann.features.movies.data.mapper.toEntity
import com.example.evaldylann.features.movies.data.remote.GhibliApi
import com.example.evaldylann.features.movies.domain.Movie
import com.example.evaldylann.features.movies.domain.MovieRepository

class MovieRepositoryImpl(
    private val api: GhibliApi,
    private val dao: MovieDao
) : MovieRepository {

    override suspend fun getMovies(forceRefresh: Boolean): Result<List<Movie>> = runCatching {
        val local = dao.getAll().map { it.toDomain() }
        if (local.isNotEmpty() && !forceRefresh) return@runCatching local
        val remote = api.movies()
        dao.upsertAll(remote.map { it.toEntity() })
        dao.getAll().map { it.toDomain() }
    }

    override suspend fun getMovie(id: String): Movie =
        api.movie(id).toDomain()

}
