package com.w36495.randomrithm.data.service

import com.w36495.randomrithm.data.entity.UserDTO
import com.w36495.randomrithm.data.entity.UserInfoDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("search/user")
    suspend fun checkUserAccount(
        @Query("query", encoded = true) query: String
    ): Response<UserDTO>

    @GET("user/show")
    suspend fun getUserInfo(
        @Query("handle") userId: String
    ): Response<UserInfoDTO>
}