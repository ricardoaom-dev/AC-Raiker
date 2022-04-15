package com.raikerxv.model.datasource

import com.raikerxv.model.RemoteConnection

class MovieRemoteDataSource(private val apiKey: String) {

    suspend fun findPopularMovies(region: String) =
        RemoteConnection.service.listPopularMovies(apiKey, region)
}