package com.w36495.randomrithm.data.repository

import com.w36495.randomrithm.data.entity.ProblemDTO
import com.w36495.randomrithm.data.entity.ProblemItem
import retrofit2.Response

interface ProblemRepository {
    suspend fun fetchProblem(problemId: Int): Response<ProblemItem>
    suspend fun fetchProblemsByTag(query: String, page: Int): Response<ProblemDTO>
}