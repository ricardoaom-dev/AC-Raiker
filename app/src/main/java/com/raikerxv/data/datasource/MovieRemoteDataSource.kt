package com.raikerxv.data.datasource

import com.raikerxv.domain.Movie

interface MovieRemoteDataSource {
    suspend fun findPopularMovies(region: String): List<Movie>
}