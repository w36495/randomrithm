package com.w36495.randomrithm.domain.entity

data class Tag (
    val id: Int,
    val key: String,
    val name: String,
    val problemCount: Int = 0
)