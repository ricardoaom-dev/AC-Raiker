package com.raikerxv.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.raikerxv.model.Movie

class DetailViewModel(movie: Movie) : ViewModel() {
    data class DetailUIState(val movie: Movie)

    private val _state = MutableLiveData(DetailUIState(movie))
    val state: LiveData<DetailUIState> get() = _state
}

@Suppress("UNCHECKED_CAST")
class DetailViewModelFactory(private val movie: Movie) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(movie) as T
    }
}