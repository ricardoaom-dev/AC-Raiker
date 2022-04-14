package com.raikerxv.ui.main

import com.raikerxv.model.Movie
import com.raikerxv.model.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainPresenter(
    private val moviesRepository: MoviesRepository,
    private val scope: CoroutineScope
) {

    interface MainView {
        fun showProgress()
        fun updateData(movies: List<Movie>)
        fun hideProgress()
        fun navigateTo(movie: Movie)
    }

    private var mainView: MainView? = null

    fun onCreate(mainView: MainView) {
        this.mainView = mainView

        scope.launch {
            mainView.showProgress()
            mainView.updateData(moviesRepository.findPopularMovies().results)
            mainView.hideProgress()
        }
    }

    fun onMovieClick(movie: Movie) {
        mainView?.navigateTo(movie)
    }

    fun onDestroy() {
        this.mainView = null
    }

}