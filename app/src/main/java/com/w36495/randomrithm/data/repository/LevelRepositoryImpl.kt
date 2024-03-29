package com.w36495.randomrithm.data.repository

import com.w36495.randomrithm.data.datasource.LevelRemoteDataSource
import com.w36495.randomrithm.data.entity.LevelDTO
import com.w36495.randomrithm.domain.repository.LevelRepository
import retrofit2.Response
import javax.inject.Inject

class LevelRepositoryImpl @Inject constructor(
    private val levelRemoteDataSource: LevelRemoteDataSource
) : LevelRepository {
    override suspend fun fetchLevels(): Response<List<LevelDTO>> {
        return levelRemoteDataSource.fetchLevels()
    }
}