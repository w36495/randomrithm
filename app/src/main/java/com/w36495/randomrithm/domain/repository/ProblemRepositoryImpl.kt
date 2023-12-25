package com.w36495.randomrithm.domain.repository

import com.w36495.randomrithm.data.datasource.ProblemRemoteDataSource
import com.w36495.randomrithm.data.entity.ProblemItem
import com.w36495.randomrithm.data.repository.ProblemRepository
import retrofit2.Response

class ProblemRepositoryImpl(
    private val problemRemoteDataSource: ProblemRemoteDataSource
) : ProblemRepository {
    override suspend fun fetchProblem(problemId: Int): Response<ProblemItem> {
        return problemRemoteDataSource.fetchProblem(problemId)
    }
}