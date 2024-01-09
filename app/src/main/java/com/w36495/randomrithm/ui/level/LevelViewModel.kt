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
    val levels: LiveData<List<LevelDTO>>
        get() = _levels

    fun getLevels(selectedLevel: Int) {
        viewModelScope.launch {
            try {
                val result = getLevelsUseCase.invoke()

                if (result.isSuccessful) {
                    result.body()?.let { response ->
                        when (selectedLevel) {
                            0 -> {
                                _levels.value = response.filter { it.level == 0 }.sortedByDescending { it.level }
                            }
                            1 -> {
                                _levels.value = response.filter { it.level in 1..5 }.sortedByDescending { it.level }
                            }
                            2 -> {
                                _levels.value = response.filter { it.level in 6..10 }.sortedByDescending { it.level }
                            }
                            3 -> {
                                _levels.value = response.filter { it.level in 11..15 }.sortedByDescending { it.level }
                            }
                            4 -> {
                                _levels.value = response.filter { it.level in 16..20 }.sortedByDescending { it.level }
                            }
                            5 -> {
                                _levels.value = response.filter { it.level in 21..25 }.sortedByDescending { it.level }
                            }
                            6 -> {
                                _levels.value = response.filter { it.level in 26..30 }.sortedByDescending { it.level }
                            }
                        }
                    }
                }
            } catch (exception: Exception) {
                exception.localizedMessage?.let {
                    Log.d(TAG, exception.localizedMessage)
                }
            }
        }
    }

    companion object {
        const val TAG: String = "LevelViewModel"
    }
}