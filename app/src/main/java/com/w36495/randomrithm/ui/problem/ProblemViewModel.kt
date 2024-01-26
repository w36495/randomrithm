package com.w36495.randomrithm.ui.problem

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.w36495.randomrithm.domain.entity.Problem
import com.w36495.randomrithm.domain.entity.Tag
import com.w36495.randomrithm.domain.usecase.GetProblemsByLevelUseCase
import com.w36495.randomrithm.domain.usecase.GetProblemsByTagUseCase
import kotlinx.coroutines.launch

class ProblemViewModel(
    private val getProblemsByLevelUseCase: GetProblemsByLevelUseCase,
    private val getProblemsByTagUseCase: GetProblemsByTagUseCase
) : ViewModel() {
    private val _problems = MutableLiveData<List<Problem>>()
    val problems: LiveData<List<Problem>>
        get() = _problems

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
                        _problems.value = tempProblems.toList()
                    }
                }
            } catch (exception: Exception) {
                exception.localizedMessage?.let { Log.d(TAG, it) } ?: Log.d(TAG, "error message == null")
            }
        }
    }

    fun getProblemsByLevel(level: Int) {
        val requestQuery = "solvable:true+tier:$level"

        viewModelScope.launch {
            try {
                val result = getProblemsByLevelUseCase.invoke(requestQuery, 1)

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
                        _problems.value = tempProblems.toList()
                    }
                }
            } catch (exception: Exception) {
                Log.d(TAG, exception.localizedMessage)
            }
        }
    }

    companion object {
        const val TAG: String = "ProblemViewModel"
    }
}