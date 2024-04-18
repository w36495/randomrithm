package com.w36495.randomrithm.domain.usecase

import android.content.Context
import com.w36495.randomrithm.domain.usecase.ChangeTagStateUseCase.Companion.TAG_STATE_KEY
import com.w36495.randomrithm.domain.usecase.ChangeTagStateUseCase.Companion.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTagStateUseCase @Inject constructor(
    private val context: Context
) {
    operator fun invoke(): Flow<Boolean> {
        return context.dataStore.data.map {
            it[TAG_STATE_KEY] ?: true
        }
    }
}