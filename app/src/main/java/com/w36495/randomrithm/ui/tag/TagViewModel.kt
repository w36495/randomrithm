package com.w36495.randomrithm.ui.tag

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.w36495.randomrithm.domain.entity.Tag
import com.w36495.randomrithm.domain.usecase.GetTagsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagViewModel @Inject constructor(
    private val getTagsUseCase: GetTagsUseCase
) : ViewModel() {
    private val _tags = MutableLiveData<List<Tag>>()
    private val _loading = MutableLiveData(false)
    val tags: LiveData<List<Tag>>
        get() = _tags
    val loading: LiveData<Boolean>
        get() = _loading

    fun getTags() {
        viewModelScope.launch {
            try {
                _loading.value = true
                val tags = getTagsUseCase.fetchTags()

                if (tags.isSuccessful) {
                    val tempTags = mutableListOf<Tag>()
                    tags.body()?.let { dto ->
                        dto.items.forEach {
                            tempTags.add(Tag(it.bojTagId, it.key, it.displayNames[0].name, it.problemCount))
                        }

                        _tags.value = tempTags.toList()
                    }
                }
            } catch (exception: Exception) {
                exception.localizedMessage?.let { Log.d(TAG, it) }
            } finally {
                _loading.value = false
            }
        }
    }

    companion object {
        const val TAG: String = "TagViewModel"
    }
}