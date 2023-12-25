package com.w36495.randomrithm.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.w36495.randomrithm.domain.usecase.GetProblemUseCase

class ProblemViewModelFactory(
    private val getProblemUseCase: GetProblemUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProblemViewModel::class.java)) {
            return ProblemViewModel(getProblemUseCase) as T
        } else {
            throw IllegalArgumentException("Unknown Problem ViewModel Factory")
        }
    }
}