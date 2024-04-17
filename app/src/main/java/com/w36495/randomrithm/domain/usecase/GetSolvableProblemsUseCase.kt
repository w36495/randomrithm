package com.w36495.randomrithm.domain.usecase

import com.w36495.randomrithm.domain.entity.Problem
import com.w36495.randomrithm.domain.entity.ProblemType
import javax.inject.Inject

class GetSolvableProblemsUseCase @Inject constructor(
    private val getProblemsUseCase: GetProblemsUseCase,
    private val getSolvedProblemsUseCase: GetSolvedProblemsUseCase,
) {
    suspend operator fun invoke(userId: String, problemType: ProblemType): List<Problem> {
        val solvableProblems = mutableListOf<Problem>()
        val problems = getProblemsUseCase(problemType)
        val solvedProblems = getSolvedProblemsUseCase(userId)

        problems.forEach { problem ->
            if (isSolvableProblem(problem, solvedProblems)) solvableProblems.addAll(problems)
        }

        return solvableProblems.toList()
    }

    private fun isSolvableProblem(problem: Problem, solvedProblem: List<Problem>): Boolean {
        var start = 0
        var end = solvedProblem.lastIndex

        while (start < end) {
            val middle = (start + end) / 2

            if (solvedProblem[middle].id == problem.id) return true
            else if (solvedProblem[middle].id < problem.id) end = middle - 1
            else start = middle + 1
        }

        return false
    }
}