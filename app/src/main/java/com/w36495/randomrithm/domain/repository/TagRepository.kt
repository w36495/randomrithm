package com.w36495.randomrithm.domain.repository

import com.w36495.randomrithm.data.entity.AlgorithmDTO
import retrofit2.Response

interface TagRepository {
    suspend fun fetchTags(): Response<AlgorithmDTO>
}