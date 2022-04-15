package com.raikerxv.data.datasource


import com.raikerxv.data.database.DBMovie
import com.raikerxv.data.database.MovieDao
import com.raikerxv.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieLocalDataSource(private val movieDao: MovieDao) {

    val movies: Flow<List<Movie>> = movieDao.getAll().map { it.toDomainModel() }

    suspend fun isEmpty(): Boolean = movieDao.movieCount() == 0

    fun findById(id: Int): Flow<Movie> = movieDao.findById(id).map { it.toDomainModel() }

    suspend fun save(movies: List<Movie>) {
        movieDao.insertMovies(movies.fromDomainModel())
    }

}

private fun List<DBMovie>.toDomainModel(): List<Movie> = map { it.toDomainModel() }

private fun DBMovie.toDomainModel(): Movie = Movie(
    id,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    favorite
)

private fun List<Movie>.fromDomainModel(): List<DBMovie> = map { it.fromDomainModel() }

private fun Movie.fromDomainModel(): DBMovie = DBMovie(
    id,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    favorite
)