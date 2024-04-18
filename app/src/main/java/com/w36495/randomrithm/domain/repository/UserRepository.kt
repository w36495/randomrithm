package com.w36495.randomrithm.domain.repository

import com.w36495.randomrithm.data.entity.UserDTO
import com.w36495.randomrithm.data.entity.UserInfoDTO
import retrofit2.Response

interface UserRepository {
    fun getCachedUserInfo(): UserInfoDTO?
    suspend fun getUser(query: String): Boolean
    suspend fun getUserInfo(userId: String): Response<UserInfoDTO>
}