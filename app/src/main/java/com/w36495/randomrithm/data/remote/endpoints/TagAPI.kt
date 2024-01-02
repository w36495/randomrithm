package com.w36495.randomrithm.data.remote.endpoints

import com.w36495.randomrithm.data.entity.AlgorithmDTO
import retrofit2.Response
import retrofit2.http.GET

interface TagAPI {
    @GET("tag/list")
    suspend fun getTags(): Response<AlgorithmDTO>
}