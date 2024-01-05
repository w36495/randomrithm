package com.w36495.randomrithm.domain.usecase

import com.w36495.randomrithm.data.entity.ProblemDTO
import com.w36495.randomrithm.domain.repository.ProblemRepositoryImpl
import retrofit2.Response

class GetProblemsByTagUseCase(
    private val problemRepository: ProblemRepositoryImpl
) {
    suspend operator fun invoke(
        query: String,
        page: Int
    ): Response<ProblemDTO> {
        return problemRepository.fetchProblemsByTag(query, page)
    }
}