package com.w36495.randomrithm.domain.repository

import androidx.lifecycle.LiveData
import com.w36495.randomrithm.data.entity.ProblemDTO
import com.w36495.randomrithm.domain.entity.Problem
import retrofit2.Response

interface ProblemRepository {
    val solvedProblems: LiveData<List<Problem>>

    suspend fun fetchProblems(query: String): Response<ProblemDTO>
    suspend fun fetchSolvedProblems(userId: String, page: Int)
}