package com.w36495.randomrithm.domain.usecase

import com.w36495.randomrithm.data.entity.ProblemDTO
import com.w36495.randomrithm.data.repository.ProblemRepositoryImpl
import retrofit2.Response

class GetProblemsByLevelUseCase(
    private val problemRepository: ProblemRepositoryImpl
) {
    suspend operator fun invoke(query: String, page: Int): Response<ProblemDTO> {
        return problemRepository.fetchProblemsByLevel(query, page)
    }
}