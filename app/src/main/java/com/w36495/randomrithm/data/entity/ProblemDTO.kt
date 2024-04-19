package com.w36495.randomrithm.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.w36495.randomrithm.domain.entity.Problem

@JsonClass(generateAdapter = true)
data class ProblemDTO(
    @Json(name = "count")
    val count: Int,
    @Json(name = "items")
    val items: List<ProblemItem>
)

@JsonClass(generateAdapter = true)
data class ProblemItem(
    @Json(name = "acceptedUserCount")
    val acceptedUserCount: Int,
    @Json(name = "averageTries")
    val averageTries: Double,
    @Json(name = "givesNoRating")
    val givesNoRating: Boolean,
    @Json(name = "isLevelLocked")
    val isLevelLocked: Boolean,
    @Json(name = "isPartial")
    val isPartial: Boolean,
    @Json(name = "isSolvable")
    val isSolvable: Boolean,
    @Json(name = "level")
    val level: Int,
    @Json(name = "metadata")
    val metadata: Metadata,
    @Json(name = "official")
    val official: Boolean,
    @Json(name = "problemId")
    val problemId: Int,
    @Json(name = "sprout")
    val sprout: Boolean,
    @Json(name = "tags")
    val tags: List<Tag>,
    @Json(name = "titleKo")
    val titleKo: String,
    @Json(name = "titles")
    val titles: List<Title>,
    @Json(name = "votedUserCount")
    val votedUserCount: Int
)
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
) {
    fun toDomainModel() = com.w36495.randomrithm.domain.entity.Tag(
        id = bojTagId,
        key = this.key,
        name = this.displayNames[0].name,
        problemCount = this.problemCount
    )
}

@JsonClass(generateAdapter = true)
class Metadata

@JsonClass(generateAdapter = true)
data class Aliase(
    @Json(name = "alias")
    val alias: String
)

fun ProblemItem.toDomainModel() = Problem(
    id = this.problemId,
    level = this.level.toString(),
    title = this.titleKo,
    tags = this.tags.map { it.toDomainModel() }
)