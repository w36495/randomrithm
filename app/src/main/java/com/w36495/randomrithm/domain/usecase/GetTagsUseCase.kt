package com.w36495.randomrithm.domain.usecase

import com.w36495.randomrithm.data.entity.AlgorithmDTO
import com.w36495.randomrithm.data.repository.TagRepositoryImpl
import retrofit2.Response

class GetTagsUseCase(
    private val tagRepository: TagRepositoryImpl
) {
    suspend fun fetchTags(): Response<AlgorithmDTO> {
        return tagRepository.fetchTags()
    }
}