package com.raikerxv.usecases

import com.raikerxv.data.MoviesRepository
import com.raikerxv.domain.Error

class SwitchMovieFavoriteUseCase(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(movie: com.raikerxv.domain.Movie): Error? = moviesRepository.switchFavorite(movie)

}