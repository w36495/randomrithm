package com.w36495.randomrithm.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.w36495.randomrithm.data.remote.endpoints.TagAPI
import com.w36495.randomrithm.data.remote.endpoints.LevelAPI
import com.w36495.randomrithm.data.remote.endpoints.ProblemAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitClient {

    private val BASE_URL: String = "https://solved.ac/api/v3/"

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        )
        .build()

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(httpClient)
        .build()

    val levelAPI: LevelAPI by lazy {
        retrofit.create(LevelAPI::class.java)
    }

    val tagAPI: TagAPI by lazy {
        retrofit.create(TagAPI::class.java)
    }

    val problemAPI: ProblemAPI by lazy {
        retrofit.create(ProblemAPI::class.java)
    }

}