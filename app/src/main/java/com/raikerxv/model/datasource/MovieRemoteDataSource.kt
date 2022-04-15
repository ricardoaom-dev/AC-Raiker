package com.raikerxv.model.datasource

import com.raikerxv.model.RegionRepository
import com.raikerxv.model.RemoteConnection

class MovieRemoteDataSource(
    private val apiKey: String, private val regionRepository: RegionRepository
) {

    suspend fun findPopularMovies() =
        RemoteConnection.service.listPopularMovies(
            apiKey,
            regionRepository.findLastRegion()
        )

}