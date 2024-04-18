package com.w36495.randomrithm.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.w36495.randomrithm.domain.entity.Tag
import com.w36495.randomrithm.domain.usecase.GetCachedUserInfoUseCase
import com.w36495.randomrithm.domain.usecase.GetTagsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTagsUseCase: GetTagsUseCase,
    private val getCachedUserInfoUseCase: GetCachedUserInfoUseCase,
) : ViewModel() {
    private var _error = MutableLiveData<String>()
    private var _tags = MutableLiveData<List<Tag>>()
    val tags: LiveData<List<Tag>> get() = _tags
    val error: LiveData<String> get() = _error
    val user = getCachedUserInfoUseCase()

    init {
        loadInitData()
    }

    private fun loadInitData() {
        viewModelScope.launch {
            _tags.value = getTagsUseCase.invoke().subList(0, 10)
        }
    }
}