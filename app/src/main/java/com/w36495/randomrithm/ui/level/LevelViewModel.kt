package com.w36495.randomrithm.ui.level

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.w36495.randomrithm.data.entity.LevelDTO
import com.w36495.randomrithm.domain.usecase.GetLevelsUseCase
import kotlinx.coroutines.launch

class LevelViewModel(
    private val getLevelsUseCase: GetLevelsUseCase
) : ViewModel() {
    private var _levels = MutableLiveData<List<LevelDTO>>()
    private var _loading = MutableLiveData(false)

    val levels: LiveData<List<LevelDTO>> get() = _levels
    val loading: LiveData<Boolean> get() = _loading

    fun getLevels(selectedLevel: Int) {
        viewModelScope.launch {
            try {
                _loading.value = true
                val result = getLevelsUseCase.invoke()

                if (result.isSuccessful) {
                    result.body()?.let { response ->
                        _levels.value = when (selectedLevel) {
                            0 -> response.filter { it.level == 0 }.sortedByDescending { it.level }
                            1 -> response.filter { it.level in 1..5 }.sortedByDescending { it.level }
                            2 -> response.filter { it.level in 6..10 }.sortedByDescending { it.level }
                            3 -> response.filter { it.level in 11..15 }.sortedByDescending { it.level }
                            4 -> response.filter { it.level in 16..20 }.sortedByDescending { it.level }
                            5 -> response.filter { it.level in 21..25 }.sortedByDescending { it.level }
                            6 -> response.filter { it.level in 26..30 }.sortedByDescending { it.level }
                            else -> emptyList()
                        }
                    }
                }
            } catch (exception: Exception) {
                exception.localizedMessage?.let {
                    Log.d(TAG, exception.localizedMessage)
                }
            } finally {
                _loading.value = false
            }
        }
    }

    companion object {
        const val TAG: String = "LevelViewModel"
    }
}