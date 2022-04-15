package com.raikerxv.framework.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM DBMovie")
    fun getAll(): Flow<List<DBMovie>>

    @Query("SELECT * FROM DBMovie WHERE id = :id")
    fun findById(id: Int): Flow<DBMovie>

    @Query("SELECT COUNT(id) FROM DBMovie")
    suspend fun movieCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<DBMovie>)

}