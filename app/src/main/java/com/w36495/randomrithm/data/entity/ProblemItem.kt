package com.w36495.randomrithm.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

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