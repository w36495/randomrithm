package com.w36495.randomrithm.domain.usecase

import com.w36495.randomrithm.data.entity.ProblemDTO
import com.w36495.randomrithm.data.entity.ProblemItem
import com.w36495.randomrithm.domain.repository.ProblemRepository
import retrofit2.Response
import javax.inject.Inject

class GetProblemsOfSproutUseCase @Inject constructor(
    private val problemRepository: ProblemRepository,
) {
    suspend operator fun invoke(): Response<ProblemDTO> {
        var totalProblemCount = 0
        val sproutProblems = mutableListOf<ProblemItem>()

        val result = problemRepository.fetchProblemsOfSprout()

        if (result.isSuccessful) {
            result.body()?.let { dto ->
                dto.forEach { dtoItem ->
                    totalProblemCount += dtoItem.problemCount
                    sproutProblems.addAll(dtoItem.problems)
                }
            }
        }

        return Response.success(
            ProblemDTO(totalProblemCount, shuffleSproutProblems(sproutProblems))
        )
    }

    private fun shuffleSproutProblems(problems: List<ProblemItem>): List<ProblemItem> {
        return problems.shuffled()
    }
}
