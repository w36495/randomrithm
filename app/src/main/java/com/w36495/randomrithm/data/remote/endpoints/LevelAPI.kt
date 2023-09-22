package com.w36495.randomrithm.data.remote.endpoints

import com.w36495.randomrithm.data.entity.Level
import retrofit2.Call
import retrofit2.http.GET

interface LevelAPI {
    @GET("problem/level")
    fun getCountByLevel(): Call<List<Level>>
}