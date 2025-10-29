package com.example.evaldylann.movies.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaldylann.features.movies.domain.GetMoviesUseCase
import com.example.evaldylann.movies.ui.model.MovieUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val getMovies: GetMoviesUseCase,
    private val soundManager: SoundManager
) : ViewModel() {

    private val _state = MutableStateFlow(MoviesState())
    val state: StateFlow<MoviesState> = _state.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun dispatch(action: MoviesAction) {
        when (action) {
            MoviesAction.Load -> load(force = false)
            MoviesAction.Refresh -> load(force = true)
            is MoviesAction.ClickSound -> soundManager.playClick()
        }
    }

    private fun load(force: Boolean) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            getMovies(force)
                .onSuccess { list ->
                    val items = list.map { m ->
                        MovieUiModel(
                            id = m.id,
                            title = m.title,
                            subtitle = "${m.director} • ${m.releaseYear}",
                            image = m.bannerUrl ?: m.posterUrl.orEmpty()
                        )
                    }
                    _state.update { MoviesState(isLoading = false, items = items, error = null) }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message ?: "Unknown error") }
                }
        }
    }
}
