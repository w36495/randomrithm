package com.w36495.randomrithm.domain.repository

import com.w36495.randomrithm.data.entity.ProblemDTO
import retrofit2.Response

interface ProblemRepository {
    suspend fun fetchProblemsByLevel(query: String, page: Int): Response<ProblemDTO>
    suspend fun fetchProblemsByTag(query: String, page: Int): Response<ProblemDTO>
    suspend fun fetchProblemsByTagAndLevel(query: String): Response<ProblemDTO>
}