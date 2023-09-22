package com.w36495.randomrithm.data.remote.endpoints

import com.w36495.randomrithm.data.entity.Algorithm
import retrofit2.Call
import retrofit2.http.GET

interface AlgorithmAPI {
    @GET("tag/list")
    fun getCountOfAlgorithm(): Call<Algorithm>
}