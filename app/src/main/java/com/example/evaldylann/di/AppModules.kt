package com.example.evaldylann.di

import androidx.room.Room
import com.example.evaldylann.features.movies.data.MovieRepositoryImpl
import com.example.evaldylann.features.movies.data.local.MovieDb
import com.example.evaldylann.features.movies.data.remote.GhibliApi
import com.example.evaldylann.features.movies.domain.GetMovieByIdUseCase
import com.example.evaldylann.features.movies.domain.GetMoviesUseCase
import com.example.evaldylann.features.movies.domain.MovieRepository
import com.example.evaldylann.movies.ui.MoviesViewModel
import com.example.evaldylann.movies.ui.SoundManager
import com.example.evaldylann.movies.ui.detail.MovieDetailViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    }
                )
            }
        }
    }
    single { GhibliApi(get<HttpClient>()) }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            MovieDb::class.java,
            "movies.db"
        )
            .fallbackToDestructiveMigration() 
            .build()
    }
    single { get<MovieDb>().movieDao() }
}


val moviesModule = module {
    single<MovieRepository> { MovieRepositoryImpl(api = get(), dao = get()) }
    factory { GetMoviesUseCase(get()) }
    factory { GetMovieByIdUseCase(get()) }

    single { SoundManager(androidContext()) }
    viewModel { MoviesViewModel(get(), get()) }
    viewModel { MovieDetailViewModel(get()) }
}
