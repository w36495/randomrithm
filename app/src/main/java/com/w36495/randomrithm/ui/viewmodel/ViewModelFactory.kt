package com.w36495.randomrithm.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.w36495.randomrithm.domain.usecase.GetProblemUseCase
import com.w36495.randomrithm.domain.usecase.GetTagsUseCase
import com.w36495.randomrithm.ui.algorithm.TagViewModel

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

class TagViewModelFactory(
    private val getTagsUseCase: GetTagsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TagViewModel::class.java)) {
            return TagViewModel(getTagsUseCase) as T
        } else {
            throw IllegalArgumentException("Unknow Tag ViewModel Factory")
        }
    }
}