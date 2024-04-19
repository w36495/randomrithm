package com.w36495.randomrithm.data.repository

import com.w36495.randomrithm.data.datasource.UserRemoteDataSource
import com.w36495.randomrithm.data.entity.UserInfoDTO
import com.w36495.randomrithm.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {
    private var user: UserInfoDTO? = null

    override fun getCacheUserInfo(): UserInfoDTO {
        return this.user!!
    }

    override fun hasCacheUserInfo(): Boolean {
        return this.user?.let { true } ?: false
    }

    override suspend fun getUser(query: String): Boolean {
        val result = userRemoteDataSource.checkUserAccount(query)

        if (result.isSuccessful) {
            result.body()?.let { dto ->
                if (dto.count == 1) {
                    saveCacheUserInfo(dto.items[0])
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
                saveCacheUserInfo(it)
                return true
            }
        }

        return false
    }

    private fun saveCacheUserInfo(user: UserInfoDTO) {
        this.user = user
    }
}