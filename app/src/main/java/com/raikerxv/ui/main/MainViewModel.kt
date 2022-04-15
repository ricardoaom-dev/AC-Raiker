package com.raikerxv.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.raikerxv.domain.GetPopularMoviesUseCase
import com.raikerxv.domain.RequestPopularMoviesUseCase
import com.raikerxv.data.Error
import com.raikerxv.data.database.Movie
import com.raikerxv.data.toError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val requestPopularMoviesUseCase: RequestPopularMoviesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MainUIState())
    val state: StateFlow<MainUIState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getPopularMoviesUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { movies -> _state.value = MainUIState(movies = movies) }
        }
    }

    fun onUIReady() {
        viewModelScope.launch {
            val error = requestPopularMoviesUseCase()
            _state.update { it.copy(error = error) }
        }
    }

    data class MainUIState(
        val loading: Boolean = false,
        val movies: List<Movie>? = null,
        val error: Error? = null
    )

}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val requestPopularMoviesUseCase: RequestPopularMoviesUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(getPopularMoviesUseCase, requestPopularMoviesUseCase) as T
    }

}