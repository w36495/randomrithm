package com.w36495.randomrithm.data.repository

import com.w36495.randomrithm.data.datasource.ProblemRemoteDataSource
import com.w36495.randomrithm.data.entity.ProblemDTO
import com.w36495.randomrithm.domain.repository.ProblemRepository
import retrofit2.Response
import javax.inject.Inject

class ProblemRepositoryImpl @Inject constructor(
    private val problemRemoteDataSource: ProblemRemoteDataSource,
) : ProblemRepository {
    override suspend fun fetchProblems(query: String): Response<ProblemDTO> {
        return problemRemoteDataSource.fetchProblems(query)
    }
    override suspend fun fetchSolvedProblems(query: String, page: Int): Response<ProblemDTO> {
        return problemRemoteDataSource.fetchSolvedProblems(query, page)
    }
}