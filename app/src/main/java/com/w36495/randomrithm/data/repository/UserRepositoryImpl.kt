package com.w36495.randomrithm.data.repository

import com.w36495.randomrithm.data.datasource.UserRemoteDataSource
import com.w36495.randomrithm.data.entity.UserDTO
import com.w36495.randomrithm.data.entity.UserInfoDTO
import com.w36495.randomrithm.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {
    override suspend fun getUser(query: String): Response<UserDTO> {
        return userRemoteDataSource.checkUserAccount(query)
    }
    override suspend fun getUserInfo(userId: String): Response<UserInfoDTO> {
        return userRemoteDataSource.getUserInfo(userId)
    }
}