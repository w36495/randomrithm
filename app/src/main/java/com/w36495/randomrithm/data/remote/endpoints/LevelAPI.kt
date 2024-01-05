package com.w36495.randomrithm.data.remote.endpoints

import com.w36495.randomrithm.data.entity.LevelDTO
import retrofit2.Response
import retrofit2.http.GET

interface LevelAPI {
    @GET("problem/level")
    suspend fun getLevels(): Response<List<LevelDTO>>
}