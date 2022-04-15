package com.raikerxv.domain

import com.raikerxv.data.Error
import com.raikerxv.data.MoviesRepository
import com.raikerxv.data.database.Movie

class SwitchMovieFavoriteUseCase(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(movie: Movie): Error? = moviesRepository.switchFavorite(movie)

}