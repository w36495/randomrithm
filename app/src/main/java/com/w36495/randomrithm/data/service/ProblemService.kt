package com.w36495.randomrithm.data.service

import com.w36495.randomrithm.data.entity.ProblemDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProblemService {
    @GET("search/problem")
    suspend fun fetchProblems(
        @Query("query", encoded = true) query: String,
        @Query("page") page: Int = 1,
        @Query("sort") sort: String = "random",
        @Query("direction") direction: String = "asc"
    ): Response<ProblemDTO>

    @GET("search/problem")
    suspend fun fetchSolvedProblems(
        @Query("query", encoded = true) query: String,
        @Query("page") page: Int = 1,
    ): Response<ProblemDTO>
}