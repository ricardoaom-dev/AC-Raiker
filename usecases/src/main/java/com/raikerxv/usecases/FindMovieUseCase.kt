package com.raikerxv.usecases

import com.raikerxv.data.MoviesRepository
import kotlinx.coroutines.flow.Flow

class FindMovieUseCase(private val moviesRepository: MoviesRepository) {

    operator fun invoke(id: Int): Flow<com.raikerxv.domain.Movie> = moviesRepository.findById(id)

}