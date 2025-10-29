package com.example.evaldylann.movies.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaldylann.features.movies.domain.GetMovieByIdUseCase
import com.example.evaldylann.features.movies.domain.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val getMovieById: GetMovieByIdUseCase
) : ViewModel() {

    data class UiState(
        val loading: Boolean = true,
        val movie: Movie? = null,
        val error: String? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun load(id: String) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true, error = null) }
            runCatching { getMovieById(id) }
                .onSuccess { _state.value = UiState(loading = false, movie = it) }
                .onFailure { _state.value = UiState(loading = false, error = it.message) }
        }
    }
}