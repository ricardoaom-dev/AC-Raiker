package com.raikerxv.model

import com.raikerxv.App
import com.raikerxv.R
import com.raikerxv.model.database.Movie
import com.raikerxv.model.datasource.MovieLocalDataSource
import com.raikerxv.model.datasource.MovieRemoteDataSource
import kotlinx.coroutines.flow.Flow

class MoviesRepository(application: App) {

    private val regionRepository = RegionRepository(application)
    private val localDataSource = MovieLocalDataSource(application.db.movieDao())
    private val remoteDataSource = MovieRemoteDataSource(application.getString(R.string.api_key))

    val popularMovies = localDataSource.movies

    fun findById(id: Int): Flow<Movie> = localDataSource.findById(id)

    suspend fun requestPopularMovies(): Error? = tryCall {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.findPopularMovies(regionRepository.findLastRegion())
            localDataSource.save(movies.results.toLocalMovies())
        }
    }

    suspend fun switchFavorite(movie: Movie) = tryCall {
        val updatedMovie = movie.copy(favorite = !movie.favorite)
        localDataSource.save(listOf(updatedMovie))
    }

}

private fun List<RemoteMovie>.toLocalMovies(): List<Movie> = map { it.toLocalMovie() }

private fun RemoteMovie.toLocalMovie(): Movie = Movie(
    id,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath ?: "",
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    false
)