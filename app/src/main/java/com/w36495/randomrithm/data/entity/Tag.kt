package com.w36495.randomrithm.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Tag(
    @Json(name = "aliases")
    val aliases: List<Aliase>,
    @Json(name = "bojTagId")
    val bojTagId: Int,
    @Json(name = "displayNames")
    val displayNames: List<DisplayName>,
    @Json(name = "isMeta")
    val isMeta: Boolean,
    @Json(name = "key")
    val key: String,
    @Json(name = "problemCount")
    val problemCount: Int
)