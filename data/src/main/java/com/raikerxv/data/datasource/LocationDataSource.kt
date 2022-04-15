package com.raikerxv.data.datasource

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}

