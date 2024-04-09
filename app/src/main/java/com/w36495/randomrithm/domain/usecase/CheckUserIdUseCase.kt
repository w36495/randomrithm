package com.w36495.randomrithm.domain.usecase

import com.w36495.randomrithm.domain.repository.UserRepository
import com.w36495.randomrithm.utils.Constants
import javax.inject.Inject

class CheckUserIdUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String): Boolean {
        if (userId.trim().isEmpty()) {
            throw IllegalArgumentException(Constants.EXCEPTION_WRONG_INPUT.message)
        }

        val result = userRepository.getUser(userId)
        if (result.isSuccessful) {
            result.body()?.let {
                if (it.count == 1) return true
            }
        }

        return false
    }
}