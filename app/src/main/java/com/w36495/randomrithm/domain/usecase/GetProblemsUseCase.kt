package com.w36495.randomrithm.domain.usecase

import com.w36495.randomrithm.data.message.ExceptionMessage
import com.w36495.randomrithm.domain.entity.Problem
import com.w36495.randomrithm.domain.entity.Tag
import com.w36495.randomrithm.domain.repository.ProblemRepository
import javax.inject.Inject

class GetProblemsUseCase @Inject constructor(
    private val problemRepository: ProblemRepository
){
    suspend operator fun invoke(query: String): List<Problem> {
        val queryPrefix = "solvable:true+"

        val problems = mutableListOf<Problem>()
        val result = problemRepository.fetchProblems(queryPrefix + query)

        if (result.isSuccessful) {
            result.body()?.let { dto ->
                if (dto.count == 0) {
                    throw IllegalStateException(ExceptionMessage.NonExistProblem.name)
                }
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