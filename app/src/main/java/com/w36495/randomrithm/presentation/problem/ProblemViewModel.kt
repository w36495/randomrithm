package com.w36495.randomrithm.presentation.problem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.w36495.randomrithm.domain.entity.Problem
import com.w36495.randomrithm.domain.entity.ProblemType
import com.w36495.randomrithm.domain.usecase.GetProblemsUseCase
import com.w36495.randomrithm.domain.usecase.GetSolvableProblemsUseCase
import com.w36495.randomrithm.domain.usecase.GetTagStateUseCase
import com.w36495.randomrithm.domain.usecase.HasCacheUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProblemViewModel @Inject constructor(
    private val getTagStateUseCase: GetTagStateUseCase,
    private val getProblemsUseCase: GetProblemsUseCase,
    private val getSolvableProblemsUseCase: GetSolvableProblemsUseCase,
    private val hasCacheUserInfoUseCase: HasCacheUserInfoUseCase,
) : ViewModel() {
    private var savedProblem: Problem? = null
    private var currentProblemIndex: Int = INIT_PROBLEM_INDEX

    private val _problemType = MutableLiveData<ProblemType>()
    private val _problems = MutableLiveData<List<Problem>>()
    private val _problem = MutableLiveData<Problem>()
    private val _loading = MutableLiveData(false)
    private val _error = MutableLiveData<String>()

    val problem: LiveData<Problem> get() = _problem
    val problems: LiveData<List<Problem>> get() = _problems
    val problemType: LiveData<ProblemType> get() = _problemType
    val loading: LiveData<Boolean> get() = _loading
    val error: LiveData<String> get() = _error
    val tagState: Flow<Boolean> = getTagStateUseCase.invoke()
    val hasCacheUserInfo = hasCacheUserInfoUseCase()

    fun initCurrentProblemType(problemType: ProblemType) {
        _problemType.value = problemType
    }

    fun saveCurrentProblem() { this.savedProblem = _problem.value }

    fun hasSavedProblem(): Boolean = savedProblem != null

    fun clearSavedProblem() { this.savedProblem = null }

    private fun getSavedProblem(): Problem {
        _loading.value = false
        return this.savedProblem!!
    }

    fun getProblem(problems: List<Problem>) {
        if (currentProblemIndex >= problems.size) getProblems(problemType.value!!)
        else _problem.value = problems[currentProblemIndex++]
    }

    fun getProblemId(): Int {
        problems.value?.let {
            return it[currentProblemIndex - 1].id
        }

        return 0
    }

    fun getNextProblem() {
        problems.value?.let {
            getProblem(it)
        }
    }

    fun getSolvableProblems(problemType: ProblemType) {
        viewModelScope.launch {
            val result = getSolvableProblemsUseCase(problemType)
            _problems.value = result
        }
    }

    fun getProblems(problemType: ProblemType) {
        currentProblemIndex = INIT_PROBLEM_INDEX

        viewModelScope.launch {
            try {
                _loading.value = true

                _problems.value = getProblemsUseCase.invoke(problemType)
            } catch (exception: Exception) {
                _error.value = exception.message
            } finally {
                _loading.value = false
            }
        }
    }

    companion object {
        private const val INIT_PROBLEM_INDEX: Int = 0
        const val TAG: String = "ProblemViewModel"
    }
}