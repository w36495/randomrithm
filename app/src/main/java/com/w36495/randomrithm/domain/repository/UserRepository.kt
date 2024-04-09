package com.w36495.randomrithm.domain.repository

import androidx.lifecycle.LiveData
import com.w36495.randomrithm.data.entity.UserDTO
import com.w36495.randomrithm.domain.entity.User
import retrofit2.Response

interface UserRepository {
    val user: LiveData<User>

    suspend fun getUser(query: String): Response<UserDTO>
    suspend fun getUserInfo(userId: String)
}