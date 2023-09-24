package com.w36495.randomrithm.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Title(
    @Json(name = "isOriginal")
    val isOriginal: Boolean,
    @Json(name = "language")
    val language: String,
    @Json(name = "languageDisplayName")
    val languageDisplayName: String,
    @Json(name = "title")
    val title: String
)