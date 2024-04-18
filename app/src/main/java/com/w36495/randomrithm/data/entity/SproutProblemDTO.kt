package com.w36495.randomrithm.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SproutProblemDTO(
    @Json(name = "category")
    val category: String,
    @Json(name = "problemCount")
    val problemCount: Int,
    @Json(name = "problems")
    val problems: List<ProblemItem>,
)