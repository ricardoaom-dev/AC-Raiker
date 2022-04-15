package com.raikerxv.usecases

import com.raikerxv.data.MoviesRepository
import com.raikerxv.domain.Error

class RequestPopularMoviesUseCase(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(): Error? = moviesRepository.requestPopularMovies()

}