package com.w36495.randomrithm.data.datasource

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
}