package com.w36495.randomrithm.data.remote.endpoints

import com.w36495.randomrithm.data.entity.ProblemDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProblemAPI {
    @GET("search/problem")
    suspend fun fetchProblemsByTag(
        @Query("query", encoded = true) query: String,
        @Query("page") page: Int,
        @Query("sort") sort: String = "random",
        @Query("direction") direction: String = "asc"
    ): Response<ProblemDTO>

    @GET("search/problem")
    suspend fun fetchProblemsByLevel(
        @Query("query", encoded = true) query: String,
        @Query("page") page: Int,
        @Query("sort") sort: String = "random",
        @Query("direction") direction: String = "asc"
    ): Response<ProblemDTO>
}