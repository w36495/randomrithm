package com.w36495.randomrithm.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.w36495.randomrithm.domain.entity.Tag

@JsonClass(generateAdapter = true)
data class AlgorithmDTO(
    @Json(name = "count")
    val count: Int,
    @Json(name = "items")
    val items: List<AlgorithmItem>
)

@JsonClass(generateAdapter = true)
data class AlgorithmItem(
    @Json(name = "aliases")
    val aliases: List<Any>,
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
) {
    fun toDomainModel() = Tag(
        id = this.bojTagId,
        key = this.key,
        name = this.displayNames[0].name,
        problemCount = this.problemCount
    )
}

@JsonClass(generateAdapter = true)
data class DisplayName(
    @Json(name = "language")
    val language: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "short")
    val short: String
)