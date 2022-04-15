package com.raikerxv.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.raikerxv.model.MoviesRepository
import com.raikerxv.model.database.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    private val _state = MutableStateFlow(MainUIState())
    val state: StateFlow<MainUIState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            moviesRepository.popularMovies.collect { movies ->
                _state.value = MainUIState(movies = movies)
            }
        }
    }

    fun onUIReady() {
        viewModelScope.launch {
            _state.value = MainUIState(loading = true)
            val hideLoader = moviesRepository.requestPopularMovies()
            if(hideLoader) _state.value = MainUIState(loading = false)
        }
    }

    data class MainUIState(
        val loading: Boolean = false,
        val movies: List<Movie>? = null
    )

}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val moviesRepository: MoviesRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(moviesRepository) as T
    }

}