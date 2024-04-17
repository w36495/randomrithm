package com.w36495.randomrithm.domain.usecase

import com.w36495.randomrithm.data.entity.ProblemDTO
import com.w36495.randomrithm.domain.entity.DetailLevelType
import com.w36495.randomrithm.domain.entity.LevelType
import com.w36495.randomrithm.domain.entity.Problem
import com.w36495.randomrithm.domain.entity.ProblemType
import com.w36495.randomrithm.domain.entity.SolvedCountType
import com.w36495.randomrithm.domain.entity.SourceType
import com.w36495.randomrithm.domain.entity.SproutType
import com.w36495.randomrithm.domain.entity.TagAndLevelType
import com.w36495.randomrithm.domain.entity.TagType
import com.w36495.randomrithm.domain.repository.ProblemRepository
import com.w36495.randomrithm.utils.toProblems
import retrofit2.Response
import javax.inject.Inject

class GetProblemsUseCase @Inject constructor(
    private val problemRepository: ProblemRepository,
    private val getProblemsOfSproutUseCase: GetProblemsOfSproutUseCase,
) {
    suspend operator fun invoke(problemType: ProblemType): List<Problem> {
        val result = when (problemType) {
            is TagType -> getProblemsByTag(problemType.tag)
            is LevelType -> getProblemsOfLevel(problemType.level)
            is DetailLevelType -> getProblemsOfDetailLevel(problemType.level)
            is TagAndLevelType -> getProblemsByTagAndLevel(problemType.tag, problemType.level)
            is SourceType -> getProblemsBySource(problemType.source)
            is SproutType -> getProblemsOfSproutUseCase()
            is SolvedCountType -> getProblemsInSolvedCount(problemType.min, problemType.max)
        }

        if (result.isSuccessful) {
            result.body()?.let {
                return it.toProblems()
            }
        }
        return emptyList()
    }

    private suspend fun getProblemsByTag(tag: String): Response<ProblemDTO> {
        val query = "tag:$tag"
        return problemRepository.fetchProblems(query)
    }

    private suspend fun getProblemsOfLevel(level: Char): Response<ProblemDTO> {
        val query = "%2A$level"
        return problemRepository.fetchProblems(query)
    }

    private suspend fun getProblemsOfDetailLevel(level: Int): Response<ProblemDTO> {
        val query = "tier:$level"
        return problemRepository.fetchProblems(query)
    }

    private suspend fun getProblemsByTagAndLevel(tag: String, level: Int): Response<ProblemDTO> {
        val replaceLevel = when (level) {
            0 -> "b"
            1 -> "s"
            2 -> "g"
            3 -> "p"
            4 -> "d"
            else -> "r"
        }
        val query = "%23$tag+*$replaceLevel"
        return problemRepository.fetchProblems(query)
    }

    private suspend fun getProblemsBySource(source: String): Response<ProblemDTO> {
        val query = "%2F$source"
        return problemRepository.fetchProblems(query)
    }

    private suspend fun getProblemsInSolvedCount(min: Int, max: Int): Response<ProblemDTO> {
        val query = StringBuilder().append("s%23")
        if (min == -1) query.append("..$max")
        else if (max == -1) query.append("$min..")
        else if (min > -1 && max > -1) query.append("$min..$max")

        return problemRepository.fetchProblems(query.toString())
    }
}