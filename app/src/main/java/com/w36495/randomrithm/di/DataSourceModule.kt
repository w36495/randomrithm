package com.w36495.randomrithm.di

import com.w36495.randomrithm.data.datasource.ProblemRemoteDataSource
import com.w36495.randomrithm.data.datasource.TagRemoteDataSource
import com.w36495.randomrithm.data.remote.endpoints.ProblemAPI
import com.w36495.randomrithm.data.remote.endpoints.TagAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    fun provideTagRemoteDataSource(
        tagAPI: TagAPI
    ): TagRemoteDataSource = TagRemoteDataSource(tagAPI)

    @Provides
    fun provideProblemRemoteDataSource(
        problemAPI: ProblemAPI
    ): ProblemRemoteDataSource = ProblemRemoteDataSource(problemAPI)
}