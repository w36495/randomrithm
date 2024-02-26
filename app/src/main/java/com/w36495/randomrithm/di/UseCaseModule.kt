package com.w36495.randomrithm.di

import android.content.Context
import com.w36495.randomrithm.domain.repository.LevelRepository
import com.w36495.randomrithm.domain.repository.ProblemRepository
import com.w36495.randomrithm.domain.repository.TagRepository
import com.w36495.randomrithm.domain.usecase.ChangeTagStateUseCase
import com.w36495.randomrithm.domain.usecase.GetLevelsUseCase
import com.w36495.randomrithm.domain.usecase.GetProblemsByLevelUseCase
import com.w36495.randomrithm.domain.usecase.GetProblemsByTagAndLevelUseCase
import com.w36495.randomrithm.domain.usecase.GetProblemsByTagUseCase
import com.w36495.randomrithm.domain.usecase.GetTagStateUseCase
import com.w36495.randomrithm.domain.usecase.GetTagsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    fun provideGetTagStateUseCase(
        @ApplicationContext context: Context
    ): GetTagStateUseCase = GetTagStateUseCase(context)

    @Provides
    fun provideChangeTagStateUseCase(
        @ApplicationContext context: Context
    ): ChangeTagStateUseCase = ChangeTagStateUseCase(context)
}