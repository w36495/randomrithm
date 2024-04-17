package com.w36495.randomrithm.domain.repository

import com.w36495.randomrithm.data.entity.UserDTO
import com.w36495.randomrithm.data.entity.UserInfoDTO
import retrofit2.Response

interface UserRepository {
    suspend fun getUser(query: String): Response<UserDTO>
    suspend fun getUserInfo(userId: String): Response<UserInfoDTO>
}