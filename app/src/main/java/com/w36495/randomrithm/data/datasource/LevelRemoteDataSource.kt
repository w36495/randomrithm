package com.w36495.randomrithm.data.datasource

import com.w36495.randomrithm.data.entity.LevelDTO
import com.w36495.randomrithm.data.remote.endpoints.LevelAPI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class LevelRemoteDataSource @Inject constructor(
    private val levelAPI: LevelAPI,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun fetchLevels(): Response<List<LevelDTO>> =
        withContext(ioDispatcher) { levelAPI.getLevels() }
}