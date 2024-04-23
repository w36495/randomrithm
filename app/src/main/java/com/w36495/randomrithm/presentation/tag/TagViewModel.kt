package com.w36495.randomrithm.presentation.tag

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.w36495.randomrithm.domain.entity.Tag
import com.w36495.randomrithm.domain.usecase.GetTagsUseCase
import com.w36495.randomrithm.domain.usecase.HasProblemOfTagUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagViewModel @Inject constructor(
    private val getTagsUseCase: GetTagsUseCase,
    private val hasProblemOfTagUseCase: HasProblemOfTagUseCase,
) : ViewModel() {
    private val _tags = MutableLiveData<List<Tag>>()
    private val _loading = MutableLiveData<Boolean>()
    private val _problemCountOfTag = MutableLiveData<List<Boolean>>()

    val tags: LiveData<List<Tag>> = _tags
    val loading: LiveData<Boolean> get() = _loading
    val problemCountOfTag: LiveData<List<Boolean>> get() = _problemCountOfTag

    init {
        getTags()
    }

    private fun getTags() {
        viewModelScope.launch {
            try {
                _loading.value = true

                _tags.value = getTagsUseCase.invoke()
            } catch (exception: Exception) {
                exception.localizedMessage?.let { Log.d(TAG, it) }
            } finally {
                _loading.value = false
            }
        }
    }

    fun hasProblemOfTag(tag: String) {
        viewModelScope.launch {
            _problemCountOfTag.value = hasProblemOfTagUseCase.invoke(tag)
        }
    }

    companion object {
        const val TAG: String = "TagViewModel"
    }
}