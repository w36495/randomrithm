package com.w36495.randomrithm.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.w36495.randomrithm.data.entity.toDomainModel
import com.w36495.randomrithm.domain.entity.Tag
import com.w36495.randomrithm.domain.entity.User
import com.w36495.randomrithm.domain.repository.UserRepository
import com.w36495.randomrithm.domain.usecase.GetTagsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val getTagsUseCase: GetTagsUseCase,
) : ViewModel() {
    private var _error = MutableLiveData<String>()
    private var _tags = MutableLiveData<List<Tag>>()
    private var _user = MutableLiveData<User>()

    val user: LiveData<User> get() = _user
    val tags: LiveData<List<Tag>> get() = _tags
    val error: LiveData<String> get() = _error

    init {
        loadInitData()
    }

    private fun loadInitData() {
        viewModelScope.launch {
            _tags.value = getTagsUseCase.invoke().subList(0, 10)
        }
    }

    fun getUserProfile(userId: String) {
        viewModelScope.launch {
            val result = userRepository.getUserInfo(userId)

            if (result.isSuccessful) {
                result.body()?.let { info ->
                    _user.value = info.toDomainModel()
                }
            }
        }
    }
}