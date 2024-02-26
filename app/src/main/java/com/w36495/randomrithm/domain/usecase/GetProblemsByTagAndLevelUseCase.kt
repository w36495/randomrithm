package com.w36495.randomrithm.domain.usecase

import com.w36495.randomrithm.domain.entity.Problem
import com.w36495.randomrithm.domain.entity.Tag
import com.w36495.randomrithm.domain.repository.ProblemRepository
import javax.inject.Inject

class GetProblemsByTagAndLevelUseCase @Inject constructor(
    private val problemRepository: ProblemRepository
) {
    suspend operator fun invoke(tag: String, level: Int): List<Problem> {
        val replaceLevel = when (level) {
            0 -> "b"
            1 -> "s"
            2 -> "g"
            3 -> "p"
            4 -> "d"
            else -> "r"
        }
        val query = "%23$tag+*$replaceLevel"

        val problems = mutableListOf<Problem>()

        val result = problemRepository.fetchProblemsByTagAndLevel(query)
        if (result.isSuccessful) {
            result.body()?.let { dto ->
                dto.items.forEach {
                    val tags = mutableListOf<Tag>()
                    it.tags.forEach { tag ->
                        tags.add(Tag(tag.bojTagId, tag.key, tag.displayNames[0].name, tag.problemCount))
                    }

                    problems.add(Problem(it.problemId, it.level.toString(), it.titleKo, tags.toList()))
                }
            }
        }

        return problems.toList()
    }
}