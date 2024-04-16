package com.w36495.randomrithm.domain.repository

import com.w36495.randomrithm.data.entity.ProblemDTO
import retrofit2.Response

interface ProblemRepository {
    suspend fun fetchProblems(query: String): Response<ProblemDTO>
    suspend fun fetchSolvedProblems(userId: String, page: Int): Response<ProblemDTO>
}