package com.w36495.randomrithm.data.datasource

import com.w36495.randomrithm.data.entity.ProblemDTO
import com.w36495.randomrithm.data.entity.SproutProblemDTO
import com.w36495.randomrithm.data.service.ProblemService
import retrofit2.Response
import javax.inject.Inject

class ProblemRemoteDataSource @Inject constructor(
    private val problemService: ProblemService,
) {
    suspend fun fetchProblems(query: String): Response<ProblemDTO> = problemService.fetchProblems(query)
    suspend fun fetchProblemsOfSprout(): Response<List<SproutProblemDTO>> {
        return problemService.fetchProblemsOfSprout()
    }
    suspend fun fetchSolvedProblems(query: String, page: Int): Response<ProblemDTO> {
        return problemService.fetchSolvedProblems(query, page)
    }
}