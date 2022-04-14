package com.raikerxv.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raikerxv.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailViewModel(movie: Movie) : ViewModel() {
    data class DetailUIState(val movie: Movie)

    private val _state = MutableStateFlow(DetailUIState(movie))
    val state: StateFlow<DetailUIState> = _state.asStateFlow()
}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val movie: Movie) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(movie) as T
    }
}