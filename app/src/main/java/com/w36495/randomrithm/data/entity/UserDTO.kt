package com.w36495.randomrithm.data.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.w36495.randomrithm.domain.entity.User

@JsonClass(generateAdapter = true)
data class UserDTO(
    @Json(name = "count")
    val count: Int,
    @Json(name = "items")
    val items: List<UserInfoDTO>
)

@JsonClass(generateAdapter = true)
data class UserInfoDTO(
    @Json(name = "arenaCompetedRoundCount")
    val arenaCompetedRoundCount: Int,
    @Json(name = "arenaMaxRating")
    val arenaMaxRating: Int,
    @Json(name = "arenaMaxTier")
    val arenaMaxTier: Int,
    @Json(name = "arenaRating")
    val arenaRating: Int,
    @Json(name = "arenaTier")
    val arenaTier: Int,
    @Json(name = "backgroundId")
    val backgroundId: String,
    @Json(name = "badgeId")
    val badgeId: Any?,
    @Json(name = "bannedUntil")
    val bannedUntil: String,
    @Json(name = "bio")
    val bio: String,
    @Json(name = "classDecoration")
    val classDecoration: String,
    @Json(name = "class")
    val classX: Int,
    @Json(name = "coins")
    val coins: Int,
    @Json(name = "handle")
    val handle: String,
    @Json(name = "joinedAt")
    val joinedAt: String,
    @Json(name = "maxStreak")
    val maxStreak: Int,
    @Json(name = "proUntil")
    val proUntil: String,
    @Json(name = "profileImageUrl")
    val profileImageUrl: Any?,
    @Json(name = "rank")
    val rank: Int,
    @Json(name = "rating")
    val rating: Int,
    @Json(name = "ratingByClass")
    val ratingByClass: Int,
    @Json(name = "ratingByProblemsSum")
    val ratingByProblemsSum: Int,
    @Json(name = "ratingBySolvedCount")
    val ratingBySolvedCount: Int,
    @Json(name = "ratingByVoteCount")
    val ratingByVoteCount: Int,
    @Json(name = "reverseRivalCount")
    val reverseRivalCount: Int,
    @Json(name = "rivalCount")
    val rivalCount: Int,
    @Json(name = "solvedCount")
    val solvedCount: Int,
    @Json(name = "stardusts")
    val stardusts: Int,
    @Json(name = "tier")
    val tier: Int,
    @Json(name = "voteCount")
    val voteCount: Int
)

fun UserInfoDTO.toDomainModel() = User(
    id = handle,
    tier = tier,
    solvedCount = solvedCount
)