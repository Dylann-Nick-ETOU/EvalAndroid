package com.example.evaldylann.features.movies.data.remote

import com.example.evaldylann.features.movies.data.dto.GhibliMovieDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.accept
import io.ktor.http.ContentType

class GhibliApi(private val client: HttpClient) {
    private val base = "https://ghibliapi.vercel.app"
    suspend fun movies(): List<GhibliMovieDto> =
        client.get("$base/films") {
            accept(ContentType.Application.Json)
        }.body()

    suspend fun movie(id: String): GhibliMovieDto =
        client.get("$base/films/$id"){
            accept(ContentType.Application.Json)
        }.body()
}
