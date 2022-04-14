package com.raikerxv.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.raikerxv.model.Movie
import com.raikerxv.model.MoviesRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    private val _state = MutableLiveData(MainUIState())
    val state: LiveData<MainUIState>
        get() {
            if (_state.value?.movies == null) {
                refresh()
            }
            return _state
        }

    private fun refresh() {
        viewModelScope.launch {
            _state.value = MainUIState(loading = true)
            _state.value = MainUIState(movies = moviesRepository.findPopularMovies().results)
        }
    }

    fun onMovieClick(movie: Movie) {
        _state.value = _state.value?.copy(navigateTo = movie)
    }

    data class MainUIState(
        val loading: Boolean = false,
        val movies: List<Movie>? = null,
        val navigateTo: Movie? = null
    )

}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val moviesRepository: MoviesRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(moviesRepository) as T
    }

}