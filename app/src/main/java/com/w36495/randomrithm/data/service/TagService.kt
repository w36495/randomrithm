package com.w36495.randomrithm.data.service

import com.w36495.randomrithm.data.entity.AlgorithmDTO
import retrofit2.Response
import retrofit2.http.GET

interface TagService {
    @GET("tag/list")
    suspend fun getTags(): Response<AlgorithmDTO>
}