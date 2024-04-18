package com.w36495.randomrithm.domain.usecase

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.w36495.randomrithm.domain.usecase.SaveUserIdUseCase.Companion.USER_ID_KEY
import com.w36495.randomrithm.domain.usecase.SaveUserIdUseCase.Companion.dataStoreForUser
import javax.inject.Inject

class ClearUserIdUseCase @Inject constructor(
    private val context: Context
) {
    suspend operator fun invoke() {
        context.dataStoreForUser.edit {
            it.remove(USER_ID_KEY)
        }
    }
}