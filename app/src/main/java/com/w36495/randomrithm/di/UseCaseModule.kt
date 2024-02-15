package com.w36495.randomrithm.di

import com.w36495.randomrithm.domain.repository.LevelRepository
import com.w36495.randomrithm.domain.repository.ProblemRepository
import com.w36495.randomrithm.domain.repository.TagRepository
import com.w36495.randomrithm.domain.usecase.GetLevelsUseCase
import com.w36495.randomrithm.domain.usecase.GetProblemsByLevelUseCase
import com.w36495.randomrithm.domain.usecase.GetProblemsByTagUseCase
import com.w36495.randomrithm.domain.usecase.GetTagsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetTagsUseCase(
        tagRepository: TagRepository
    ): GetTagsUseCase = GetTagsUseCase(tagRepository)

    @Provides
    fun provideGetLevelsUseCase(
        levelRepository: LevelRepository
    ): GetLevelsUseCase = GetLevelsUseCase(levelRepository)

    @Provides
    fun provideGetProblemsByLevelUseCase(
        problemRepository: ProblemRepository
    ): GetProblemsByLevelUseCase = GetProblemsByLevelUseCase(problemRepository)

    @Provides
    fun provideGetProblemsByTagUseCase(
        problemRepository: ProblemRepository
    ): GetProblemsByTagUseCase = GetProblemsByTagUseCase(problemRepository)
}