package com.w36495.randomrithm.domain.usecase

import com.w36495.randomrithm.data.entity.LevelDTO
import com.w36495.randomrithm.data.repository.LevelRepositoryImpl
import retrofit2.Response

class GetLevelsUseCase(
    private val levelRepository: LevelRepositoryImpl
) {
    suspend operator fun invoke(): Response<List<LevelDTO>> {
        return levelRepository.fetchLevels()
    }
}