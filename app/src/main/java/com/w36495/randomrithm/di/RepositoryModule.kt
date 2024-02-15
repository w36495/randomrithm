package com.w36495.randomrithm.di

import com.w36495.randomrithm.data.repository.TagRepositoryImpl
import com.w36495.randomrithm.domain.repository.TagRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindTagRepository(
        tagRepositoryImpl: TagRepositoryImpl
    ): TagRepository
}