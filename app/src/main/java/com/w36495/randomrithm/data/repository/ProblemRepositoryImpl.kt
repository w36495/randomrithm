package com.w36495.randomrithm.data.repository

import com.w36495.randomrithm.data.datasource.ProblemRemoteDataSource
import com.w36495.randomrithm.data.entity.ProblemDTO
import com.w36495.randomrithm.data.entity.ProblemItem
import com.w36495.randomrithm.data.entity.SproutProblemDTO
import com.w36495.randomrithm.domain.repository.ProblemRepository
import com.w36495.randomrithm.utils.Constants
import retrofit2.Response
import javax.inject.Inject

class ProblemRepositoryImpl @Inject constructor(
    private val problemRemoteDataSource: ProblemRemoteDataSource,
) : ProblemRepository {
    private var solvedProblems: List<ProblemItem>? = null

    override fun getCacheSolvedProblems(): List<ProblemItem> {
        when (hasCacheSolvedProblems()) {
            true -> return this.solvedProblems!!
            false -> throw IllegalStateException(Constants.EXCEPTION_DATA_ROAD_FAILED.message)
        }
    }

    override fun setCacheSolvedProblems(problems: List<ProblemItem>) {
        this.solvedProblems = problems
    }

    override suspend fun fetchProblems(query: String): Response<ProblemDTO> {
        return problemRemoteDataSource.fetchProblems(query)
    }
    override suspend fun fetchProblemsOfSprout(): Response<List<SproutProblemDTO>> {
        return problemRemoteDataSource.fetchProblemsOfSprout()
    }
    override suspend fun fetchSolvedProblems(query: String, page: Int): Response<ProblemDTO> {
        return problemRemoteDataSource.fetchSolvedProblems(query, page)
    }

    private fun hasCacheSolvedProblems(): Boolean {
        return solvedProblems?.let { true } ?: false
    }
}