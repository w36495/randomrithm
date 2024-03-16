package com.w36495.randomrithm.ui.problem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.w36495.randomrithm.data.message.ExceptionMessage
import com.w36495.randomrithm.domain.entity.Problem
import com.w36495.randomrithm.domain.entity.ProblemType
import com.w36495.randomrithm.domain.usecase.GetProblemsUseCase
import com.w36495.randomrithm.domain.usecase.GetTagStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProblemViewModel @Inject constructor(
    private val getTagStateUseCase: GetTagStateUseCase,
    private val getProblemsUseCase: GetProblemsUseCase
) : ViewModel() {
    private var savedProblem: Problem? = null
    private var currentProblemIndex: Int = INIT_PROBLEM_INDEX

    private val _problemType = MutableLiveData<ProblemType>()
    private val _problems = MutableLiveData<List<Problem>>()
    private val _problem = MutableLiveData<Problem>()
    private val _loading = MutableLiveData(false)
    private val _error = MutableLiveData("")

    val problem: LiveData<Problem> get() = _problem
    val loading: LiveData<Boolean> get() = _loading
    val error: LiveData<String> get() = _error
    val tagState: Flow<Boolean> = getTagStateUseCase.invoke()

    fun initCurrentProblemType(problemType: ProblemType) {
        _problemType.value = problemType

        judgeCurrentProblemType()
    }

    fun getProblem() {
        when (hasSavedProblem()) {
            true -> _problem.value = getSavedProblem()
            false -> {
                _problems.value?.let { problems ->
                    if (currentProblemIndex >= problems.size) judgeCurrentProblemType()
                    else _problem.value = problems[currentProblemIndex++]
                } ?: { _error.value = ExceptionMessage.NonExistProblem.message }
            }
        }
    }

    fun saveCurrentProblem() { this.savedProblem = _problem.value }

    fun hasSavedProblem(): Boolean = savedProblem != null

    fun clearSavedProblem() { this.savedProblem = null }

    private fun getSavedProblem(): Problem {
        _loading.value = false
        return this.savedProblem!!
    }

    private fun judgeCurrentProblemType() {
        _problemType.value?.run {
            tag?.let { tag ->
                level?.let { level ->
                    if (level == ALL_LEVEL) getProblemsByTag(tag)
                    else getProblemByTagAndLevel(tag, level)
                } ?: getProblemsByTag(tag)
            } ?: level?.let { level ->
                getProblemsByLevel(level)
            }
            source?.let { source -> getProblemsBySourceOfProblem(source) }
        }
    }

    private fun getProblemByTagAndLevel(tag: String, level: Int) {
        val replaceLevel = when (level) {
            0 -> "b"
            1 -> "s"
            2 -> "g"
            3 -> "p"
            4 -> "d"
            else -> "r"
        }
        val query = "%23$tag+*$replaceLevel"
        getProblems(query)
    }

    private fun getProblemsByTag(tagKey: String) {
        val query = "tag:$tagKey"
        getProblems(query)
    }

    private fun getProblemsByLevel(level: Int) {
        val query = "tier:$level"
        getProblems(query)
    }

    private fun getProblemsBySourceOfProblem(selectedSource: String) {
        val query = "%2F$selectedSource"
        getProblems(query)
    }

    private fun getProblems(query: String) {
        currentProblemIndex = INIT_PROBLEM_INDEX

        viewModelScope.launch {
            try {
                _loading.value = true
                delay(500)

                _problems.value = getProblemsUseCase.invoke(query)
                getProblem()
            } catch (exception: Exception) {
                _error.value = exception.message
            } finally {
                _loading.value = false
            }
        }
    }

    companion object {
        private const val ALL_LEVEL: Int = -1
        private const val INIT_PROBLEM_INDEX: Int = 0
        const val TAG: String = "ProblemViewModel"
    }
}