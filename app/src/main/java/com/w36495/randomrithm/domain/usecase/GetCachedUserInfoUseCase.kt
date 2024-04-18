package com.w36495.randomrithm.domain.usecase

import com.w36495.randomrithm.data.entity.toDomainModel
import com.w36495.randomrithm.domain.entity.User
import com.w36495.randomrithm.domain.repository.UserRepository
import com.w36495.randomrithm.utils.Constants
import javax.inject.Inject

class GetCachedUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(): User {
        val result = userRepository.getCacheUserInfo()

        val user = result?.let {
            it.toDomainModel()
        } ?: throw IllegalStateException(Constants.EXCEPTION_NOT_EXIST_USER.message)

        return user
    }
}