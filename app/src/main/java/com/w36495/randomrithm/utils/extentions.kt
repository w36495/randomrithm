package com.w36495.randomrithm.utils

import android.os.Bundle
import com.w36495.randomrithm.data.entity.LevelDTO
import com.w36495.randomrithm.data.entity.ProblemDTO
import com.w36495.randomrithm.domain.entity.Problem
import com.w36495.randomrithm.domain.entity.ProblemType
import com.w36495.randomrithm.domain.entity.Tag
import com.w36495.randomrithm.presentation.problem.ProblemFragment

fun List<LevelDTO>.sortedByLevel(start: Int, end: Int): List<LevelDTO> {
    return if (start == 0 && end == 0) {
        this.filter { it.level == 0 }.sortedByDescending { it.level }
    } else {
        this.filter { it.level in start..end }.sortedByDescending { it.level }
    }
}

fun ProblemDTO.toProblems(): List<Problem> {
    val problems = mutableListOf<Problem>()
    this.items.forEach {
        val tags = mutableListOf<Tag>()
        it.tags.forEach { tag ->
            tags.add(Tag(tag.bojTagId, tag.key, tag.displayNames[0].name, tag.problemCount))
        }

        problems.add(Problem(it.problemId, it.level.toString(), it.titleKo, tags.toList()))
    }

    return problems.toList()
}

fun Bundle.putProblemType(problemType: ProblemType): Bundle {
    return this.apply {
        putSerializable(ProblemFragment.ARGUMENT_TAG, problemType)
    }
}