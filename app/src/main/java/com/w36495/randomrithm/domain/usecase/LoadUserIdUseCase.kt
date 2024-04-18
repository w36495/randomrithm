package com.w36495.randomrithm.domain.usecase

import android.content.Context
import com.w36495.randomrithm.domain.usecase.SaveUserIdUseCase.Companion.USER_ID_KEY
import com.w36495.randomrithm.domain.usecase.SaveUserIdUseCase.Companion.dataStoreForUser
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadUserIdUseCase @Inject constructor(
    private val context: Context
) {
    operator fun invoke(): Flow<String?> {
        return context.dataStoreForUser.data.map {
            it[USER_ID_KEY]
        }
    }
}