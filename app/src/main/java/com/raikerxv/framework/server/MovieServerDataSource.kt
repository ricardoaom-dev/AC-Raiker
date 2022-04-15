package com.raikerxv.framework.server

import com.raikerxv.framework.server.RemoteConnection
import com.raikerxv.framework.server.RemoteMovie
import com.raikerxv.data.datasource.MovieRemoteDataSource
import com.raikerxv.domain.Movie

class MovieServerDataSource(private val apiKey: String) : MovieRemoteDataSource {

    override suspend fun findPopularMovies(region: String) =
        RemoteConnection.service.listPopularMovies(apiKey, region)
            .results.toDomainModel()
}

private fun List<RemoteMovie>.toDomainModel(): List<Movie> = map { it.toDomainModel() }

private fun RemoteMovie.toDomainModel(): Movie =
    Movie(
        id,
        title,
        overview,
        releaseDate,
        "https://image.tmdb.org/t/p/w185/$posterPath",
        backdropPath?.let { "https://image.tmdb.org/t/p/w780/$it" } ?: "",
        originalLanguage,
        originalTitle,
        popularity,
        voteAverage,
        false
    )