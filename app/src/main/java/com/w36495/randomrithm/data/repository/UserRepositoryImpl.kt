package com.w36495.randomrithm.data.repository

import com.w36495.randomrithm.data.datasource.UserRemoteDataSource
import com.w36495.randomrithm.data.entity.UserInfoDTO
import com.w36495.randomrithm.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {
    private var user: UserInfoDTO? = null

    override fun getCachedUserInfo(): UserInfoDTO? {
        return this.user
    }

    override suspend fun getUser(query: String): Boolean {
        val result = userRemoteDataSource.checkUserAccount(query)

        if (result.isSuccessful) {
            result.body()?.let { dto ->
                if (dto.count == 1) {
                    user = dto.items[0]
                    return true
                }
            }
        }

        return false
    }
    override suspend fun getUserInfo(userId: String): Boolean {
        val result = userRemoteDataSource.getUserInfo(userId)

        if (result.isSuccessful) {
            result.body()?.let {
                user = it
                return true
            }
        }

        return false
    }
}