package com.w36495.randomrithm.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.w36495.randomrithm.domain.usecase.CheckUserIdUseCase
import com.w36495.randomrithm.domain.usecase.GetCacheUserInfoUseCase
import com.w36495.randomrithm.domain.usecase.LoadUserIdUseCase
import com.w36495.randomrithm.domain.usecase.SaveUserIdUseCase
import com.w36495.randomrithm.domain.usecase.SetCacheUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val checkUserIdUseCase: CheckUserIdUseCase,
    private val getCacheUserInfoUseCase: GetCacheUserInfoUseCase,
    private val saveUserIdUseCase: SaveUserIdUseCase,
    private val loadUserIdUseCase: LoadUserIdUseCase,
    private var setCacheUserIdUseCase: SetCacheUserIdUseCase,
) : ViewModel() {
    private var _cacheState = MutableLiveData<Boolean>()
    private var _loginState = MutableLiveData<Boolean>()
    private var _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    val loginState: LiveData<Boolean> get() = _loginState
    val loadUserId: Flow<String?> = loadUserIdUseCase.invoke()
    val cacheState: LiveData<Boolean> = _cacheState

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
            userId = getCacheUserInfoUseCase().id
        } catch (exception: Exception) {
            _error.value = exception.message.toString()
        }

        return userId
    }

    fun saveUserId(userId: String) {
        viewModelScope.launch {
            saveUserIdUseCase(userId)
        }
    }

    fun setCacheUserId(userId: String) {
        viewModelScope.launch {
            val result = setCacheUserIdUseCase(userId)
            _cacheState.value = result
        }
    }

    companion object {
        private const val TAG: String = "LoginViewModel"
    }
}