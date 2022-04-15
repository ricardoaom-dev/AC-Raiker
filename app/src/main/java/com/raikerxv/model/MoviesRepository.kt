package com.raikerxv.model

import com.raikerxv.model.RemoteMovie as RemoteMovie
import com.raikerxv.model.database.Movie as LocalMovie
import com.raikerxv.App
import com.raikerxv.R
import com.raikerxv.model.datasource.MovieLocalDataSource
import com.raikerxv.model.datasource.MovieRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(application: App) {

    private val localDataSource = MovieLocalDataSource(application.db.movieDao())
    private val remoteDataSource = MovieRemoteDataSource(application.getString(R.string.api_key), RegionRepository(application))

    val popularMovies = localDataSource.movies

    suspend fun requestPopularMovies() = withContext(Dispatchers.IO) {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.findPopularMovies()
            localDataSource.save(movies.results.toLocalMovies())
        }
    }

}

private fun List<RemoteMovie>.toLocalMovies(): List<LocalMovie> = map { it.toLocalMovie() }

private fun RemoteMovie.toLocalMovie(): LocalMovie = LocalMovie(
    id,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath ?: "",
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage
)