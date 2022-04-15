package com.raikerxv.data.datasource

import com.raikerxv.data.RemoteConnection

class MovieRemoteDataSource(private val apiKey: String) {

    suspend fun findPopularMovies(region: String) =
        RemoteConnection.service.listPopularMovies(apiKey, region)
}