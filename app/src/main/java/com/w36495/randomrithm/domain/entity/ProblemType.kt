package com.w36495.randomrithm.domain.entity

import java.io.Serializable

data class ProblemType(
    val tag: String? = null,
    val level: Int? = null,
    val source: String? = null
) : Serializable