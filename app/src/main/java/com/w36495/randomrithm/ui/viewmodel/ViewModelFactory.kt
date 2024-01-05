package com.w36495.randomrithm.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.w36495.randomrithm.domain.usecase.GetLevelsUseCase
import com.w36495.randomrithm.domain.usecase.GetProblemsByLevelUseCase
import com.w36495.randomrithm.domain.usecase.GetProblemsByTagUseCase
import com.w36495.randomrithm.domain.usecase.GetTagsUseCase
import com.w36495.randomrithm.ui.algorithm.TagViewModel
import com.w36495.randomrithm.ui.level.LevelViewModel
import com.w36495.randomrithm.ui.problem.ProblemViewModel

class ProblemViewModelFactory(
    private val getProblemUseCase: GetProblemsByLevelUseCase,
    private val getProblemsByTagUseCase: GetProblemsByTagUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProblemViewModel::class.java)) {
            return ProblemViewModel(getProblemUseCase, getProblemsByTagUseCase) as T
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