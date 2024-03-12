package com.w36495.randomrithm.domain.usecase

import com.w36495.randomrithm.domain.repository.ProblemRepository
import javax.inject.Inject

class HasProblemOfTagUseCase @Inject constructor(
    private val problemRepository: ProblemRepository
) {
    suspend operator fun invoke(tag: String, level: Char): Boolean {
        val query = "solvable:true+%23$tag+*$level"

        val result = problemRepository.fetchProblems(query)
        if (result.isSuccessful) {
            result.body()?.let {
                if (it.count == 0) return false
            }
        }

        return true
    }
}