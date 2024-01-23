package com.w36495.randomrithm.data.repository

import com.w36495.randomrithm.data.datasource.TagRemoteDataSource
import com.w36495.randomrithm.data.entity.AlgorithmDTO
import com.w36495.randomrithm.domain.repository.TagRepository
import retrofit2.Response

class TagRepositoryImpl(
    private val tagRemoteDataSource: TagRemoteDataSource
) : TagRepository {
    override suspend fun fetchTags(): Response<AlgorithmDTO> {
        return tagRemoteDataSource.fetchTags()
    }
}