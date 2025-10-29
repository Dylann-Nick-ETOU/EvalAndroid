package com.example.evaldylann.movies.ui

import com.example.evaldylann.movies.ui.model.MovieUiModel

/**
 *
 */
data class MoviesState(
    val isLoading: Boolean = false,
    val items: List<MovieUiModel> = emptyList(),
    val error: String? = null
)

/**
 *
 */
sealed interface MoviesAction {
    data object Load : MoviesAction
    data object Refresh : MoviesAction
    data class ClickSound(val id: String) : MoviesAction
}