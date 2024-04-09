package com.w36495.randomrithm.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.w36495.randomrithm.data.datasource.UserRemoteDataSource
import com.w36495.randomrithm.data.entity.UserDTO
import com.w36495.randomrithm.data.entity.toDomainModel
import com.w36495.randomrithm.domain.entity.User
import com.w36495.randomrithm.domain.repository.UserRepository
import com.w36495.randomrithm.utils.Constants
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {
    private var _user = MutableLiveData<User>()
    override val user: LiveData<User> get() = _user

    override suspend fun getUser(query: String): Response<UserDTO> {
        return userRemoteDataSource.checkUserAccount(query)
    }

    override suspend fun getUserInfo(userId: String) {
        val result = userRemoteDataSource.getUserInfo(userId)

        if (result.isSuccessful) {
            result.body()?.let { info ->
                _user.value = info.toDomainModel()
            }
        } else {
            throw IllegalStateException(Constants.EXCEPTION_DATA_ROAD_FAILED.message)
        }
    }
}