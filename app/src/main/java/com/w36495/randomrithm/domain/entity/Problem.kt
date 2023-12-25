package com.w36495.randomrithm.domain.entity

data class Problem(
    val id: Int,
    val level: String,
    val title: String,
    val algorithms: List<String>
)