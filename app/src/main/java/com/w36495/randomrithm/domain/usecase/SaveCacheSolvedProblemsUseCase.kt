package com.w36495.randomrithm.domain.usecase

import com.w36495.randomrithm.domain.repository.ProblemRepository
import javax.inject.Inject

class SaveCacheSolvedProblemsUseCase @Inject constructor(
    private val problemRepository: ProblemRepository,
    private val getSolvedProblemsUseCase: GetSolvedProblemsUseCase,
) {
    suspend operator fun invoke() {
        problemRepository.setCacheSolvedProblems(getSolvedProblemsUseCase())
    }
}