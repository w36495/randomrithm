package com.w36495.randomrithm.di

import com.w36495.randomrithm.domain.repository.TagRepository
import com.w36495.randomrithm.domain.usecase.GetTagsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetTagsUseCase(
        tagRepository: TagRepository
    ): GetTagsUseCase = GetTagsUseCase(tagRepository)
}