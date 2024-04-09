package com.w36495.randomrithm.di

import com.w36495.randomrithm.data.repository.LevelRepositoryImpl
import com.w36495.randomrithm.data.repository.ProblemRepositoryImpl
import com.w36495.randomrithm.data.repository.TagRepositoryImpl
import com.w36495.randomrithm.data.repository.UserRepositoryImpl
import com.w36495.randomrithm.domain.repository.LevelRepository
import com.w36495.randomrithm.domain.repository.ProblemRepository
import com.w36495.randomrithm.domain.repository.TagRepository
import com.w36495.randomrithm.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindTagRepository(
        tagRepositoryImpl: TagRepositoryImpl
    ): TagRepository

    @Binds
    abstract fun bindLevelRepository(
        levelRepositoryImpl: LevelRepositoryImpl
    ): LevelRepository

    @Binds
    abstract fun bindProblemRepository(
        problemRepositoryImpl: ProblemRepositoryImpl
    ): ProblemRepository

    @Binds
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository
}