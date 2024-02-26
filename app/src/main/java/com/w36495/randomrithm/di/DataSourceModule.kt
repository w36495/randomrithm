package com.w36495.randomrithm.di

import com.w36495.randomrithm.data.datasource.LevelRemoteDataSource
import com.w36495.randomrithm.data.datasource.ProblemRemoteDataSource
import com.w36495.randomrithm.data.datasource.TagRemoteDataSource
import com.w36495.randomrithm.data.service.LevelService
import com.w36495.randomrithm.data.service.ProblemService
import com.w36495.randomrithm.data.service.TagService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    fun provideTagRemoteDataSource(
        tagService: TagService
    ): TagRemoteDataSource = TagRemoteDataSource(tagService)

    @Provides
    fun provideLevelRemoteDataSource(
        levelService: LevelService
    ): LevelRemoteDataSource = LevelRemoteDataSource(levelService)

    @Provides
    fun provideProblemRemoteDataSource(
        problemService: ProblemService
    ): ProblemRemoteDataSource = ProblemRemoteDataSource(problemService)
}