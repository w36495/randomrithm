package com.w36495.randomrithm.domain.usecase

import com.w36495.randomrithm.data.entity.LevelDTO
import com.w36495.randomrithm.data.repository.LevelRepositoryImpl
import com.w36495.randomrithm.domain.repository.LevelRepository
import retrofit2.Response
import javax.inject.Inject

class GetLevelsUseCase @Inject constructor(
    private val levelRepository: LevelRepository
) {
    suspend operator fun invoke(): Response<List<LevelDTO>> {
        return levelRepository.fetchLevels()
    }
}