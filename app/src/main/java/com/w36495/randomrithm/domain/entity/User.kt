package com.w36495.randomrithm.domain.entity

data class User(
    val id: String,
    val tier: Int,
    val solvedCount: Int
)