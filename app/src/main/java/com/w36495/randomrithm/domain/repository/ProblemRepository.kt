package com.w36495.randomrithm.domain.repository

import com.w36495.randomrithm.data.entity.ProblemDTO
import com.w36495.randomrithm.data.entity.ProblemItem
import com.w36495.randomrithm.data.entity.SproutProblemDTO
import retrofit2.Response

interface ProblemRepository {
    fun getCacheSolvedProblems(): List<ProblemItem>
    fun setCacheSolvedProblems(problems: List<ProblemItem>)
    suspend fun fetchProblems(query: String): Response<ProblemDTO>
    suspend fun fetchProblemsOfSprout(): Response<List<SproutProblemDTO>>
    suspend fun fetchSolvedProblems(userId: String, page: Int): Response<ProblemDTO>
}