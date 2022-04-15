package com.raikerxv.usecases

import com.raikerxv.data.MoviesRepository

class RequestPopularMoviesUseCase(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(): com.raikerxv.domain.Error? = moviesRepository.requestPopularMovies()

}