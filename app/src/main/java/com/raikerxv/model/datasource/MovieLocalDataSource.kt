package com.raikerxv.model.datasource

import com.raikerxv.model.database.Movie
import com.raikerxv.model.database.MovieDao
import kotlinx.coroutines.flow.Flow

class MovieLocalDataSource(private val movieDao: MovieDao) {

    val movies: Flow<List<Movie>> = movieDao.getAll()

    fun isEmpty(): Boolean = movieDao.movieCount() == 0

    fun save(movies: List<Movie>) {
        movieDao.insertMovies(movies)
    }

}