package com.w36495.randomrithm.domain.repository

import com.w36495.randomrithm.data.entity.UserDTO
import retrofit2.Response

interface UserRepository {
    suspend fun getUser(query: String): Response<UserDTO>
}