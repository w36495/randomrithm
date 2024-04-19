package com.w36495.randomrithm.domain.usecase

import com.w36495.randomrithm.data.entity.toDomainModel
import com.w36495.randomrithm.domain.entity.Problem
import com.w36495.randomrithm.domain.repository.ProblemRepository
import javax.inject.Inject

class GetCacheSolvedProblemsUseCase @Inject constructor(
    private val problemRepository: ProblemRepository,
) {
    operator fun invoke(): List<Problem> {
        return problemRepository.getCacheSolvedProblems().map {
            it.toDomainModel()
        }
    }
}