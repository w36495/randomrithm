package com.w36495.randomrithm.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DisplayName(
    @Json(name = "language")
    val language: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "short")
    val short: String
)