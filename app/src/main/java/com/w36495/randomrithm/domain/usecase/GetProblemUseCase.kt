package com.w36495.randomrithm.domain.usecase

import com.w36495.randomrithm.data.entity.ProblemItem
import com.w36495.randomrithm.domain.repository.ProblemRepositoryImpl
import retrofit2.Response

class GetProblemUseCase(
    private val problemRepository: ProblemRepositoryImpl
) {
    suspend fun fetchProblem(problemId: Int): Response<ProblemItem> {
        return problemRepository.fetchProblem(problemId)
    }
}