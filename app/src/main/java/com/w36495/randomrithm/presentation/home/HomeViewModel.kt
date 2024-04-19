package com.w36495.randomrithm.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.w36495.randomrithm.domain.entity.Tag
import com.w36495.randomrithm.domain.usecase.GetCacheUserInfoUseCase
import com.w36495.randomrithm.domain.usecase.GetTagsUseCase
import com.w36495.randomrithm.domain.usecase.SaveCacheSolvedProblemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTagsUseCase: GetTagsUseCase,
    private val getCacheUserInfoUseCase: GetCacheUserInfoUseCase,
    private val saveCacheSolvedProblemsUseCase: SaveCacheSolvedProblemsUseCase,

) : ViewModel() {
    private var _error = MutableLiveData<String>()
    private var _tags = MutableLiveData<List<Tag>>()
    val tags: LiveData<List<Tag>> get() = _tags
    val error: LiveData<String> get() = _error
    val user = getCacheUserInfoUseCase()

    init {
        loadInitData()
    }

    private fun loadInitData() {
        viewModelScope.launch {
            async { saveCacheSolvedProblemsUseCase() }.await()
            _tags.value = getTagsUseCase.invoke().subList(0, 10)
        }
    }
}