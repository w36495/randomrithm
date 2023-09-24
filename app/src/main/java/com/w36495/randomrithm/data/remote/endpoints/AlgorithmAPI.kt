package com.w36495.randomrithm.data.remote.endpoints

import com.w36495.randomrithm.data.entity.Algorithm
import com.w36495.randomrithm.data.entity.Problem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AlgorithmAPI {
    @GET("tag/list")
    fun getCountOfAlgorithm(): Call<Algorithm>

    @GET("search/problem")
    fun getProblemList(@Query("query") tag: String): Call<Problem>
}