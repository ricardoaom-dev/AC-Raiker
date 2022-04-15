package com.raikerxv.usecases

import com.raikerxv.data.MoviesRepository
import com.raikerxv.domain.Error
import com.raikerxv.domain.Movie

class SwitchMovieFavoriteUseCase(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(movie: Movie): Error? = moviesRepository.switchFavorite(movie)

}