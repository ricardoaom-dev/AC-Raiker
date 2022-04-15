package com.raikerxv.usecases

import com.raikerxv.data.MoviesRepository
import com.raikerxv.domain.Movie
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCase(private val moviesRepository: MoviesRepository) {

    operator fun invoke(): Flow<List<Movie>> = moviesRepository.popularMovies

}