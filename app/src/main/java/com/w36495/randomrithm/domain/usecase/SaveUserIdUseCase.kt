package com.w36495.randomrithm.domain.usecase

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import javax.inject.Inject

class SaveUserIdUseCase @Inject constructor(
    private val context: Context
) {
    suspend operator fun invoke(userId: String) {
        context.dataStoreForUser.edit {
            it[USER_ID_KEY] = userId
        }
    }

    companion object {
        val Context.dataStoreForUser: DataStore<Preferences> by preferencesDataStore(name = "user")
        val USER_ID_KEY = stringPreferencesKey("userId")
    }
}