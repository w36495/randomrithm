package com.w36495.randomrithm.ui.algorithm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.w36495.randomrithm.domain.entity.Problem
import com.w36495.randomrithm.domain.entity.Tag
import com.w36495.randomrithm.domain.usecase.GetProblemsByTagUseCase
import com.w36495.randomrithm.domain.usecase.GetTagsUseCase
import kotlinx.coroutines.launch

class TagViewModel(
    private val getTagsUseCase: GetTagsUseCase,
    private val getProblemsByTagUseCase: GetProblemsByTagUseCase
) : ViewModel() {

    private val _tags = MutableLiveData<List<Tag>>()
    private val _problems = MutableLiveData<List<Problem>>()
    val tags: LiveData<List<Tag>>
        get() = _tags
    val problems: LiveData<List<Problem>>
        get() = _problems

    fun getTags() {
        viewModelScope.launch {
            try {
                val tags = getTagsUseCase.fetchTags()

                if (tags.isSuccessful) {
                    val tempTags = mutableListOf<Tag>()
                    tags.body()?.let { dto ->
                        dto.items.forEach {
                            tempTags.add(Tag(it.bojTagId, it.displayNames[0].name, it.problemCount))
                        }

                        _tags.value = tempTags.toList()
                    }
                }
            } catch (exception: Exception) {
                Log.d(TAG, exception.localizedMessage)
            }
        }
    }

    fun getProblemsByTag(tagKey: String) {
        val requestQuery = "solvable:true+tag:$tagKey"

        viewModelScope.launch {
            try {
                val result = getProblemsByTagUseCase.invoke(requestQuery, 1)

                if (result.isSuccessful) {
                    val tempProblems = mutableListOf<Problem>()
                    result.body()?.let { dto ->
                        dto.items.forEach {
                            val tags = mutableListOf<Tag>()
                            it.tags.forEach { tag ->
                                tags.add(Tag(tag.bojTagId, tag.key, tag.displayNames[0].name, tag.problemCount))
                            }

                            tempProblems.add(Problem(it.problemId, it.level.toString(), it.titleKo, tags.toList()))
                        }

                        _problems.value = tempProblems
                    }
                }
            } catch (exception: Exception) {
                Log.d(TAG, exception.localizedMessage)
            }
        }
    }

    companion object {
        const val TAG: String = "TagViewModel"
    }
}