package com.w36495.randomrithm.domain.usecase

import com.w36495.randomrithm.data.entity.toDomainModel
import com.w36495.randomrithm.domain.entity.Problem
import com.w36495.randomrithm.domain.repository.ProblemRepository
import com.w36495.randomrithm.domain.repository.UserRepository
import com.w36495.randomrithm.utils.Constants
import javax.inject.Inject

class GetSolvedProblemsUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val problemRepository: ProblemRepository
) {
    suspend operator fun invoke(): List<Problem> {
        return getUserInfo()
    }

    private suspend fun getUserInfo(): List<Problem> {
        val user = userRepository.getCacheUserInfo().toDomainModel()
        return getSolvedProblems(user.id, user.solvedCount)
    }

    private suspend fun getSolvedProblems(userId: String, solvedProblemCount: Int): List<Problem> {
        val solvedProblems = mutableListOf<Problem>()
        val lastPage = calculatePageOfProblems(solvedProblemCount)
        val query = "%40$userId"

        (FIRST_PAGE..lastPage).forEach { page ->
            val result = problemRepository.fetchSolvedProblems(query, page)

            if (result.isSuccessful) {
                result.body()?.let { problemDto ->
                    solvedProblems.addAll(problemDto.items.map { it.toDomainModel() })
                }
            } else {
                throw IllegalStateException(Constants.EXCEPTION_DATA_ROAD_FAILED.message)
            }
        }

        return solvedProblems.toList()
    }

    private fun calculatePageOfProblems(solvedProblemCount: Int): Int {
        return if (solvedProblemCount % 50 != 0) (solvedProblemCount / 50) + 1
        else solvedProblemCount / 50
    }

    companion object {
        private const val FIRST_PAGE: Int = 1
    }
}