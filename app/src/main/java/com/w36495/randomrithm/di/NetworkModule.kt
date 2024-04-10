package com.w36495.randomrithm.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.w36495.randomrithm.data.service.LevelService
import com.w36495.randomrithm.data.service.ProblemService
import com.w36495.randomrithm.data.service.TagService
import com.w36495.randomrithm.data.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL: String = "https://solved.ac/api/v3/"

    @Provides
    @Singleton
    fun provideLoggerInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(
        logger: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideLevelAPI(retrofit: Retrofit): LevelService = retrofit.create(LevelService::class.java)

    @Provides
    @Singleton
    fun provideTagAPI(retrofit: Retrofit): TagService = retrofit.create(TagService::class.java)

    @Provides
    @Singleton
    fun provideProblemService(retrofit: Retrofit): ProblemService = retrofit.create(ProblemService::class.java)

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService = retrofit.create(UserService::class.java)
}