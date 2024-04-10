package com.w36495.randomrithm.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.w36495.randomrithm.domain.repository.ProblemRepository
import com.w36495.randomrithm.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val problemRepository: ProblemRepository,
) : ViewModel() {
    private var _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    val user = userRepository.user

    fun fetchSolvedProblems(userId: String, solvedProblemCount: Int) {
        val lastPage = calculatePageOfProblems(solvedProblemCount)

        (FIRST_PAGE..lastPage).forEach { page ->
            viewModelScope.launch {
                try {
                    problemRepository.fetchSolvedProblems(userId, page)
                } catch (exception : Exception) {
                    _error.value = exception.message
                }
            }
        }
    }

    private fun calculatePageOfProblems(solvedProblemCount: Int): Int {
        return if (solvedProblemCount % 50 != 0) (solvedProblemCount / 50) + 1
        else solvedProblemCount / 50
    }

    companion object {
        private const val FIRST_PAGE: Int = 1
    }
}