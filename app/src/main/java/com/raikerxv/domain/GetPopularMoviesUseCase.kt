package com.raikerxv.domain

import com.raikerxv.data.MoviesRepository
import com.raikerxv.data.database.Movie
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCase(private val moviesRepository: MoviesRepository) {

    operator fun invoke(): Flow<List<Movie>> = moviesRepository.popularMovies

}