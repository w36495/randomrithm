package com.w36495.randomrithm.data.datasource

import com.w36495.randomrithm.data.entity.AlgorithmDTO
import com.w36495.randomrithm.data.service.TagService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class TagRemoteDataSource @Inject constructor(
    private val tagService: TagService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun fetchTags(): Response<AlgorithmDTO> {
        return withContext(ioDispatcher) {
            tagService.getTags()
        }
    }
}