package com.w36495.randomrithm.ui.level

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.w36495.randomrithm.data.entity.LevelDTO
import com.w36495.randomrithm.domain.usecase.GetLevelsUseCase
import com.w36495.randomrithm.utils.sortedByLevel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LevelViewModel @Inject constructor(
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
                            0 -> response.sortedByLevel(0, 0)
                            1 -> response.sortedByLevel(1, 5)
                            2 -> response.sortedByLevel(6, 10)
                            3 -> response.sortedByLevel(11, 15)
                            4 -> response.sortedByLevel(16, 20)
                            5 -> response.sortedByLevel(21, 25)
                            6 -> response.sortedByLevel(26, 30)
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