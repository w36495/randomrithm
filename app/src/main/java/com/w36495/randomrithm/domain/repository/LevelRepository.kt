package com.w36495.randomrithm.domain.repository

import com.w36495.randomrithm.data.entity.LevelDTO
import retrofit2.Response

interface LevelRepository {
    suspend fun fetchLevels(): Response<List<LevelDTO>>
}