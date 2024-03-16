package com.w36495.randomrithm.domain.usecase

import com.w36495.randomrithm.data.message.ExceptionMessage
import com.w36495.randomrithm.domain.entity.Problem
import com.w36495.randomrithm.domain.repository.ProblemRepository
import com.w36495.randomrithm.utils.toProblems
import javax.inject.Inject

class GetProblemsUseCase @Inject constructor(
    private val problemRepository: ProblemRepository
){
    suspend operator fun invoke(query: String): List<Problem> {
        val queryPrefix = "solvable:true+"
        val result = problemRepository.fetchProblems(queryPrefix + query)

        if (result.isSuccessful) {
            result.body()?.let { dto ->
                if (dto.count == 0) {
                    throw IllegalStateException(ExceptionMessage.NonExistProblem.message)
                }

                return dto.toProblems()
            }
        }

        return emptyList()
    }
}