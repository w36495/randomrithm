package com.w36495.randomrithm.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.w36495.randomrithm.data.datasource.ProblemRemoteDataSource
import com.w36495.randomrithm.data.entity.ProblemDTO
import com.w36495.randomrithm.data.entity.toDomainModel
import com.w36495.randomrithm.domain.entity.Problem
import com.w36495.randomrithm.domain.repository.ProblemRepository
import com.w36495.randomrithm.utils.Constants
import retrofit2.Response
import javax.inject.Inject

class ProblemRepositoryImpl @Inject constructor(
    private val problemRemoteDataSource: ProblemRemoteDataSource,
) : ProblemRepository {
    private val _solvedProblems = MutableLiveData(emptyList<Problem>())
    override val solvedProblems: LiveData<List<Problem>> get() = _solvedProblems

    override suspend fun fetchProblems(query: String): Response<ProblemDTO> {
        return problemRemoteDataSource.fetchProblems(query)
    }

    override suspend fun fetchSolvedProblems(userId: String, page: Int) {
        val query = "%40$userId"
        val result = problemRemoteDataSource.fetchSolvedProblems(query, page)

        if (result.isSuccessful) {
            result.body()?.let { problemDto ->
                _solvedProblems.value = _solvedProblems.value?.plus(
                    problemDto.items.map { it.toDomainModel() }
                )
            }
        } else {
            throw IllegalStateException(Constants.EXCEPTION_DATA_ROAD_FAILED.message)
        }
    }
}