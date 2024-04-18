package com.w36495.randomrithm.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.w36495.randomrithm.domain.usecase.CheckUserIdUseCase
import com.w36495.randomrithm.domain.usecase.GetCachedUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val checkUserIdUseCase: CheckUserIdUseCase,
    private val getCachedUserInfoUseCase: GetCachedUserInfoUseCase,
) : ViewModel() {
    private var _loginState = MutableLiveData<Boolean>()
    private var _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    val loginState: LiveData<Boolean> get() = _loginState

    fun checkUserAccount(userId: String) {
        viewModelScope.launch {
            try {
                _loginState.value = checkUserIdUseCase.invoke(userId)
            } catch (exception: Exception) {
                _error.value = exception.message
            }
        }
    }

    fun getUserId(): String? {
        var userId: String? = null

        try {
            userId = getCachedUserInfoUseCase().id
        } catch (exception: Exception) {
            _error.value = exception.message.toString()
        }

        return userId
    }

    companion object {
        private const val TAG: String = "LoginViewModel"
    }
}