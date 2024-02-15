package com.w36495.randomrithm.domain.usecase

import com.w36495.randomrithm.data.entity.AlgorithmDTO
import com.w36495.randomrithm.domain.repository.TagRepository
import retrofit2.Response
import javax.inject.Inject

class GetTagsUseCase @Inject constructor(
    private val tagRepository: TagRepository
) {
    suspend fun fetchTags(): Response<AlgorithmDTO> {
        return tagRepository.fetchTags()
    }
}