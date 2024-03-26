package com.w36495.randomrithm.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.w36495.randomrithm.domain.usecase.ChangeTagStateUseCase
import com.w36495.randomrithm.domain.usecase.GetTagStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val changeTagStateUseCase: ChangeTagStateUseCase,
    private val getTagStateUseCase: GetTagStateUseCase
) : ViewModel() {
    val tagState: Flow<Boolean> = getTagStateUseCase.invoke()

    fun changeTagState(isChecked: Boolean) {
        viewModelScope.launch {
            changeTagStateUseCase.invoke(isChecked)
        }
    }
}