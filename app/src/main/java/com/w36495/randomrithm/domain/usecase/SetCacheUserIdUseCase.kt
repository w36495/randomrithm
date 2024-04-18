package com.w36495.randomrithm.domain.usecase

import com.w36495.randomrithm.domain.repository.UserRepository
import javax.inject.Inject

class SetCacheUserIdUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(userId: String): Boolean {
        return userRepository.getUserInfo(userId)
    }
}