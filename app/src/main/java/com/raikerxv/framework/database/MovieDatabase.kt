package com.raikerxv.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DBMovie::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}