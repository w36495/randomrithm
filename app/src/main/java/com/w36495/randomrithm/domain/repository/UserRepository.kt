package com.w36495.randomrithm.domain.repository

import com.w36495.randomrithm.data.entity.UserInfoDTO

interface UserRepository {
    fun getCacheUserInfo(): UserInfoDTO
    fun hasCacheUserInfo(): Boolean
    suspend fun getUser(query: String): Boolean
    suspend fun getUserInfo(userId: String): Boolean
}