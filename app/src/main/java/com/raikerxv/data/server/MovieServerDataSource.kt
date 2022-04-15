package com.raikerxv.data.server

import arrow.core.Either
import com.raikerxv.data.datasource.MovieRemoteDataSource
import com.raikerxv.data.tryCall
import com.raikerxv.domain.Error
import com.raikerxv.domain.Movie

class MovieServerDataSource(private val apiKey: String) : MovieRemoteDataSource {

    override suspend fun findPopularMovies(region: String): Either<Error, List<Movie>> = tryCall {
        RemoteConnection.service.listPopularMovies(apiKey, region)
            .results.toDomainModel()
    }

}

private fun List<RemoteMovie>.toDomainModel(): List<com.raikerxv.domain.Movie> = map { it.toDomainModel() }

private fun RemoteMovie.toDomainModel(): com.raikerxv.domain.Movie =
    com.raikerxv.domain.Movie(
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