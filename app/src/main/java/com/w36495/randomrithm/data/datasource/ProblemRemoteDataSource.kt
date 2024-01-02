package com.w36495.randomrithm.data.datasource

import com.w36495.randomrithm.data.entity.ProblemDTO
import com.w36495.randomrithm.data.entity.ProblemItem
import com.w36495.randomrithm.data.remote.endpoints.ProblemAPI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class ProblemRemoteDataSource(
    private val problemAPI: ProblemAPI,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun fetchProblem(problemId: Int): Response<ProblemItem> =
        withContext(ioDispatcher) { problemAPI.fetchProblem(problemId) }

    suspend fun fetchProblemsByTag(query: String, page: Int): Response<ProblemDTO> =
        withContext(ioDispatcher) { problemAPI.fetchProblemsByTag(query, page) }
}