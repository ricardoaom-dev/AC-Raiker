package com.raikerxv.data.datasource

import arrow.core.Either
import com.raikerxv.domain.Error
import com.raikerxv.domain.Movie

interface MovieRemoteDataSource {
    suspend fun findPopularMovies(region: String): Either<Error, List<Movie>>
}