package com.raikerxv.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.raikerxv.domain.Movie
import com.raikerxv.usecases.FindMovieUseCase
import com.raikerxv.usecases.SwitchMovieFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailViewModel(
    movieId: Int,
    findMovieUseCase: FindMovieUseCase,
    private val switchMovieFavoriteUseCase: SwitchMovieFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DetailUIState())
    val state: StateFlow<DetailUIState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            findMovieUseCase(movieId).collect {
                _state.value = DetailUIState(it)
            }
        }
    }

    fun onFavoriteClicked() {
        viewModelScope.launch {
            _state.value.movie?.let { switchMovieFavoriteUseCase(it) }
        }
    }

    data class DetailUIState(val movie: Movie? = null)

}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(
    private val movieId: Int, private val findMovieUseCase: FindMovieUseCase,
    private val switchMovieFavoriteUseCase: SwitchMovieFavoriteUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(movieId, findMovieUseCase, switchMovieFavoriteUseCase) as T
    }
}