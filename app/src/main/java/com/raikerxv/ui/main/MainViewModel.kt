package com.raikerxv.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.raikerxv.model.Movie
import com.raikerxv.model.MoviesRepository
import com.raikerxv.ui.detail.DetailViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(MainUIState())
    val state: StateFlow<MainUIState> = _state.asStateFlow()

    private val _events = Channel<DetailUIEvent>()
    val events = _events.receiveAsFlow()

    init {
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch {
            _state.value = MainUIState(loading = true)
            _state.value = MainUIState(movies = moviesRepository.findPopularMovies().results)
        }
    }

    fun onMovieClick(movie: Movie) {
        viewModelScope.launch { _events.send(DetailUIEvent.NavigateTo(movie)) }
    }

    data class MainUIState(
        val loading: Boolean = false,
        val movies: List<Movie>? = null
    )

    sealed interface DetailUIEvent {
        data class NavigateTo(val movie: Movie) : DetailUIEvent
    }

}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val moviesRepository: MoviesRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(moviesRepository) as T
    }

}