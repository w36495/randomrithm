package com.w36495.randomrithm.domain.usecase

import com.w36495.randomrithm.domain.entity.Problem
import com.w36495.randomrithm.domain.entity.ProblemType
import javax.inject.Inject

class GetSolvableProblemsUseCase @Inject constructor(
    private val getProblemsUseCase: GetProblemsUseCase,
    private val getCacheSolvedProblemsUseCase: GetCacheSolvedProblemsUseCase,
) {
    suspend operator fun invoke(problemType: ProblemType): List<Problem> {
        val solvableProblems = mutableListOf<Problem>()
        val problems = getProblemsUseCase(problemType)
        val solvedProblems = getCacheSolvedProblemsUseCase()

        problems.forEach { problem ->
            if (isSolvableProblem(problem, solvedProblems)) solvableProblems.add(problem)
        }

        return solvableProblems.toList()
    }

    private fun isSolvableProblem(problem: Problem, solvedProblem: List<Problem>): Boolean {
        var start = 0
        var end = solvedProblem.lastIndex

        while (start < end) {
            val middle = (start + end) / 2

            if (solvedProblem[middle].id == problem.id) return false
            else if (solvedProblem[middle].id < problem.id) start = middle + 1
            else end = middle - 1
        }

        return true
    }
}