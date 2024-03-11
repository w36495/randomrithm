package com.w36495.randomrithm.data.datasource

import com.w36495.randomrithm.data.entity.ProblemDTO
import com.w36495.randomrithm.data.service.ProblemService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Response
import javax.inject.Inject

class ProblemRemoteDataSource @Inject constructor(
    private val problemService: ProblemService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun fetchProblems(query: String): Response<ProblemDTO> = problemService.fetchProblems(query)
}