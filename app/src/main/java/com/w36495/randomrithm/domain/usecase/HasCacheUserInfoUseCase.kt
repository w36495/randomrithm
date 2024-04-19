package com.w36495.randomrithm.domain.usecase

import com.w36495.randomrithm.domain.repository.UserRepository
import javax.inject.Inject

class HasCacheUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(): Boolean {
        return userRepository.hasCacheUserInfo()
    }
}