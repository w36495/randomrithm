package com.w36495.randomrithm.data.datasource

import com.w36495.randomrithm.data.entity.ProblemDTO
import com.w36495.randomrithm.data.service.ProblemService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class ProblemRemoteDataSource @Inject constructor(
    private val problemService: ProblemService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun fetchProblemsByLevel(query: String, page: Int): Response<ProblemDTO> =
        withContext(ioDispatcher) { problemService.fetchProblemsByLevel(query, page) }

    suspend fun fetchProblemsByTag(query: String, page: Int): Response<ProblemDTO> =
        withContext(ioDispatcher) { problemService.fetchProblemsByTag(query, page) }

    suspend fun fetchProblemsByTagAndLevel(query: String): Response<ProblemDTO> =
        problemService.fetchProblemsByTagAndLevel(query)
}