package com.w36495.randomrithm.domain.usecase

import com.w36495.randomrithm.data.entity.toDomainModel
import com.w36495.randomrithm.domain.entity.Tag
import com.w36495.randomrithm.domain.repository.TagRepository
import javax.inject.Inject

class GetTagsUseCase @Inject constructor(
    private val tagRepository: TagRepository
) {
    suspend fun getTags(): List<Tag> {
        return tagRepository.getTags().map { it.toDomainModel() }
    }
}