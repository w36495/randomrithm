package com.w36495.randomrithm.di

import android.content.Context
import com.w36495.randomrithm.domain.usecase.ChangeTagStateUseCase
import com.w36495.randomrithm.domain.usecase.ClearUserIdUseCase
import com.w36495.randomrithm.domain.usecase.GetTagStateUseCase
import com.w36495.randomrithm.domain.usecase.LoadUserIdUseCase
import com.w36495.randomrithm.domain.usecase.SaveUserIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetTagStateUseCase(
        @ApplicationContext context: Context
    ): GetTagStateUseCase = GetTagStateUseCase(context)

    @Provides
    fun provideChangeTagStateUseCase(
        @ApplicationContext context: Context
    ): ChangeTagStateUseCase = ChangeTagStateUseCase(context)

    @Provides
    fun provideSaveUserIdUseCase(
        @ApplicationContext context: Context
    ): SaveUserIdUseCase = SaveUserIdUseCase(context)

    @Provides
    fun provideLoadUserIdUseCase(
        @ApplicationContext context: Context
    ): LoadUserIdUseCase = LoadUserIdUseCase(context)

    @Provides
    fun provideClearUserIdUseCase(
        @ApplicationContext context: Context
    ): ClearUserIdUseCase = ClearUserIdUseCase(context)
}