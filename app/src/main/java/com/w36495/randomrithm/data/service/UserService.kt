package com.w36495.randomrithm.data.service

import com.w36495.randomrithm.data.entity.LevelDTO
import com.w36495.randomrithm.data.entity.UserDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("search/user")
    suspend fun checkUserAccount(
        @Query("query", encoded = true) query: String
    ): Response<UserDTO>
}