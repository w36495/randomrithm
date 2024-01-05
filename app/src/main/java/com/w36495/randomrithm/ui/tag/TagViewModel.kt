package com.w36495.randomrithm.ui.tag

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.w36495.randomrithm.domain.entity.Tag
import com.w36495.randomrithm.domain.usecase.GetTagsUseCase
import kotlinx.coroutines.launch

class TagViewModel(
    private val getTagsUseCase: GetTagsUseCase
) : ViewModel() {
    private val _tags = MutableLiveData<List<Tag>>()
    val tags: LiveData<List<Tag>>
        get() = _tags

    fun getTags() {
        viewModelScope.launch {
            try {
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
            }
        }
    }

    companion object {
        const val TAG: String = "TagViewModel"
    }
}