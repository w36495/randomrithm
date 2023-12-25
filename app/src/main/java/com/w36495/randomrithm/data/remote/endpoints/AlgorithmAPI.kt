package com.w36495.randomrithm.data.remote.endpoints

import com.w36495.randomrithm.data.entity.AlgorithmDTO
import com.w36495.randomrithm.data.entity.ProblemDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AlgorithmAPI {
    @GET("tag/list")
    fun getCountOfAlgorithm(): Call<AlgorithmDTO>

    @GET("search/problem")
    fun getProblemList(@Query("query") tag: String): Call<ProblemDTO>
}