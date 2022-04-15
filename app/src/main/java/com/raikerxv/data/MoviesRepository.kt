package com.raikerxv.data

import com.raikerxv.App
import com.raikerxv.R
import com.raikerxv.data.datasource.MovieLocalDataSource
import com.raikerxv.data.datasource.MovieRemoteDataSource
import com.raikerxv.domain.Movie
import com.raikerxv.framework.datasource.MovieRoomDataSource
import com.raikerxv.framework.datasource.MovieServerDataSource
import kotlinx.coroutines.flow.Flow

class MoviesRepository(application: App) {

    private val regionRepository = RegionRepository(application)
    private val localDataSource: MovieLocalDataSource = MovieRoomDataSource(application.db.movieDao())
    private val remoteDataSource: MovieRemoteDataSource = MovieServerDataSource(application.getString(R.string.api_key))

    val popularMovies = localDataSource.movies

    fun findById(id: Int): Flow<Movie> = localDataSource.findById(id)

    suspend fun requestPopularMovies(): Error? = tryCall {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.findPopularMovies(regionRepository.findLastRegion())
            localDataSource.save(movies)
        }
    }

    suspend fun switchFavorite(movie: Movie) = tryCall {
        val updatedMovie = movie.copy(favorite = !movie.favorite)
        localDataSource.save(listOf(updatedMovie))
    }

}