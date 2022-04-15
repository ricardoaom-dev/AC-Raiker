package com.raikerxv.usecases

import com.raikerxv.data.MoviesRepository
import com.raikerxv.domain.Movie
import kotlinx.coroutines.flow.Flow

class FindMovieUseCase(private val moviesRepository: MoviesRepository) {

    operator fun invoke(id: Int): Flow<Movie> = moviesRepository.findById(id)

}