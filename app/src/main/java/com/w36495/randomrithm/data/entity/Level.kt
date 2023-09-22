package com.w36495.randomrithm.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Level(
    @Json(name = "count")
    val count: Int,
    @Json(name = "level")
    val level: Int
)