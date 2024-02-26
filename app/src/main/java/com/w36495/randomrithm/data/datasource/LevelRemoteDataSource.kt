package com.w36495.randomrithm.data.datasource

import com.w36495.randomrithm.data.entity.LevelDTO
import com.w36495.randomrithm.data.service.LevelService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class LevelRemoteDataSource @Inject constructor(
    private val levelService: LevelService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun fetchLevels(): Response<List<LevelDTO>> =
        withContext(ioDispatcher) { levelService.getLevels() }
}