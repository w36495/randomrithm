package com.w36495.randomrithm.domain.entity

sealed class ProblemType: java.io.Serializable

data class TagType(
    val tag: String,
) : ProblemType()


data class LevelType(
    val level: Int,
) : ProblemType()


data class SourceType(
    val source: String,
) : ProblemType()


data class TagAndLevelType(
    val tag: String,
    val level: Int,
) : ProblemType()