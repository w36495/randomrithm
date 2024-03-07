package com.w36495.randomrithm.ui.problem

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.w36495.randomrithm.domain.entity.Problem
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

    private val _problems = MutableLiveData<List<Problem>>()
    private val _loading = MutableLiveData(false)
    val problems: LiveData<List<Problem>>
        get() = _problems
    val loading: LiveData<Boolean>
        get() = _loading

    val tagState: Flow<Boolean> = getTagStateUseCase.invoke()

    fun getSavedProblem(): Problem {
        _loading.value = false
        return this.savedProblem!!
    }

    fun saveCurrentProblem(problem: Problem) {
        this.savedProblem = problem
    }

    fun hasSavedProblem(): Boolean = savedProblem != null

    fun clearSavedProblem() {
        this.savedProblem = null
    }

    fun getProblemByTagAndLevel(tag: String, level: Int) {
        val replaceLevel = when (level) {
            0 -> "b"
            1 -> "s"
            2 -> "g"
            3 -> "p"
            4 -> "d"
            else -> "r"
        }
        val query = "%23$tag+*$replaceLevel"

        viewModelScope.launch {
            try {
                _loading.value = true
                delay(500)

                _problems.value = getProblemsUseCase.invoke(query)
            } catch (exception: Exception) {
                exception.localizedMessage?.let { Log.d(TAG, it) } ?: Log.d(TAG, "error message == null")
            } finally {
                _loading.value = false
            }
        }
    }

    fun getProblemsByTag(tagKey: String) {
        val query = "tag:$tagKey"

        viewModelScope.launch {
            try {
                _loading.value = true
                delay(500)

                _problems.value = getProblemsUseCase.invoke(query)
            } catch (exception: Exception) {
                exception.localizedMessage?.let { Log.d(TAG, it) } ?: Log.d(TAG, "error message == null")
            } finally {
                _loading.value = false
            }
        }
    }

    fun getProblemsByLevel(level: Int) {
        val query = "tier:$level"

        viewModelScope.launch {
            try {
                _loading.value = true
                delay(500)

                _problems.value = getProblemsUseCase.invoke(query)
            } catch (exception: Exception) {
                Log.d(TAG, exception.localizedMessage)
            } finally {
                _loading.value = false
            }
        }
    }

    fun getProblemsBySourceOfProblem(selectedSource: String) {
        val query = "%2F$selectedSource"

        viewModelScope.launch {
            try {
                _loading.value = true
                delay(500)

                Log.d(TAG, getProblemsUseCase.invoke(query).toString())
            } catch (exception: Exception) {

            } finally {
                _loading.value = false
            }
        }

    }

    companion object {
        const val TAG: String = "ProblemViewModel"
    }
}