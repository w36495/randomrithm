package com.w36495.randomrithm.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.w36495.randomrithm.domain.usecase.GetLevelsUseCase
import com.w36495.randomrithm.domain.usecase.GetProblemsByTagUseCase
import com.w36495.randomrithm.domain.usecase.GetTagsUseCase
import com.w36495.randomrithm.ui.algorithm.TagViewModel
import com.w36495.randomrithm.ui.level.LevelViewModel

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
    private val getTagsUseCase: GetTagsUseCase,
    private val getProblemsByTagUseCase: GetProblemsByTagUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TagViewModel::class.java)) {
            return TagViewModel(getTagsUseCase, getProblemsByTagUseCase) as T
        } else {
            throw IllegalArgumentException("Unknow Tag ViewModel Factory")
        }
    }
}

class LevelViewModelFactory(
    private val getLevelsUseCase: GetLevelsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LevelViewModel::class.java)) {
            return LevelViewModel(getLevelsUseCase) as T
        } else {
            throw IllegalArgumentException("Unknow Level ViewModel Factory")
        }
    }
}