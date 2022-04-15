package com.raikerxv.data.database


import com.raikerxv.data.datasource.MovieLocalDataSource
import com.raikerxv.data.tryCall
import com.raikerxv.domain.Error
import com.raikerxv.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRoomDataSource(private val movieDao: MovieDao) : MovieLocalDataSource {

    override val movies: Flow<List<Movie>> = movieDao.getAll().map { it.toDomainModel() }

    override suspend fun isEmpty(): Boolean = movieDao.movieCount() == 0

    override fun findById(id: Int): Flow<Movie> = movieDao.findById(id).map { it.toDomainModel() }

    override suspend fun save(movies: List<Movie>): Error? = tryCall {
        movieDao.insertMovies(movies.fromDomainModel())
    }.fold(
        ifLeft = { it },
        ifRight = { null }
    )

}

private fun List<DBMovie>.toDomainModel(): List<com.raikerxv.domain.Movie> = map { it.toDomainModel() }

private fun DBMovie.toDomainModel(): com.raikerxv.domain.Movie = com.raikerxv.domain.Movie(
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

private fun List<com.raikerxv.domain.Movie>.fromDomainModel(): List<DBMovie> = map { it.fromDomainModel() }

private fun com.raikerxv.domain.Movie.fromDomainModel(): DBMovie = DBMovie(
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