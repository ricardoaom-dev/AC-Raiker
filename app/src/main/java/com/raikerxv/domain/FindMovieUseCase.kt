package com.raikerxv.domain

import com.raikerxv.data.MoviesRepository
import com.raikerxv.data.database.Movie
import kotlinx.coroutines.flow.Flow

class FindMovieUseCase(private val moviesRepository: MoviesRepository) {

    operator fun invoke(id: Int): Flow<Movie> = moviesRepository.findById(id)

}