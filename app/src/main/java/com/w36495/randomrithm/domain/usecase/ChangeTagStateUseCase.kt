package com.w36495.randomrithm.domain.usecase

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
val TAG_STATE_KEY = booleanPreferencesKey("tag_state")

class ChangeTagStateUseCase @Inject constructor(
    private val context: Context
) {
    suspend operator fun invoke(changedState: Boolean = true) {
        context.dataStore.edit { settings ->
            settings[TAG_STATE_KEY] = changedState
        }
    }
}