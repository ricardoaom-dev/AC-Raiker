package com.raikerxv.usecases

import com.raikerxv.data.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCase(private val moviesRepository: MoviesRepository) {

    operator fun invoke(): Flow<List<com.raikerxv.domain.Movie>> = moviesRepository.popularMovies

}