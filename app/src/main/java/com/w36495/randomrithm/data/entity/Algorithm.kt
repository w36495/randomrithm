package com.w36495.randomrithm.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Algorithm(
    @Json(name = "count")
    val count: Int,
    @Json(name = "items")
    val items: List<AlgorithmItem>
)