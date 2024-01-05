package com.w36495.randomrithm.domain.repository

import com.w36495.randomrithm.data.datasource.ProblemRemoteDataSource
import com.w36495.randomrithm.data.entity.ProblemDTO
import com.w36495.randomrithm.data.repository.ProblemRepository
import retrofit2.Response

class ProblemRepositoryImpl(
    private val problemRemoteDataSource: ProblemRemoteDataSource
) : ProblemRepository {
    override suspend fun fetchProblemsByLevel(query: String, page: Int): Response<ProblemDTO> {
        return problemRemoteDataSource.fetchProblemsByLevel(query, page)
    }

    override suspend fun fetchProblemsByTag(query: String, page: Int): Response<ProblemDTO> {
        return problemRemoteDataSource.fetchProblemsByTag(query, page)
    }
}