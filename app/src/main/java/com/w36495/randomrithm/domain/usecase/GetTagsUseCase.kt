package com.w36495.randomrithm.domain.usecase

import com.w36495.randomrithm.data.entity.toDomainModel
import com.w36495.randomrithm.domain.entity.Tag
import com.w36495.randomrithm.domain.repository.TagRepository
import javax.inject.Inject

class GetTagsUseCase @Inject constructor(
    private val tagRepository: TagRepository
) {
    suspend operator fun invoke(): List<Tag> {
        val result = tagRepository.fetchTags()

        if (result.isSuccessful) {
            result.body()?.let { dto ->
                return dto.items.map {
                    it.toDomainModel()
                }
            }
        }

        return emptyList()
    }
}