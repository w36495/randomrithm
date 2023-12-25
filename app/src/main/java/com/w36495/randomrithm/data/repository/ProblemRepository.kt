package com.w36495.randomrithm.data.repository

import com.w36495.randomrithm.data.entity.ProblemItem
import retrofit2.Response

interface ProblemRepository {
    suspend fun fetchProblem(problemId: Int): Response<ProblemItem>
}