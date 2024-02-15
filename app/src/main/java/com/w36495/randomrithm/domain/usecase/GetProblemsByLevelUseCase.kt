package com.w36495.randomrithm.domain.usecase

import com.w36495.randomrithm.data.entity.ProblemDTO
import com.w36495.randomrithm.domain.repository.ProblemRepository
import retrofit2.Response
import javax.inject.Inject

class GetProblemsByLevelUseCase @Inject constructor(
    private val problemRepository: ProblemRepository
) {
    suspend operator fun invoke(query: String, page: Int): Response<ProblemDTO> {
        return problemRepository.fetchProblemsByLevel(query, page)
    }
}