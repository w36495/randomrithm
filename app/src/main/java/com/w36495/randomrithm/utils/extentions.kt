package com.w36495.randomrithm.utils

import com.w36495.randomrithm.data.entity.LevelDTO

fun List<LevelDTO>.sortedByLevel(start: Int, end: Int): List<LevelDTO> {
    return if (start == 0 && end == 0) {
        this.filter { it.level == 0 }.sortedByDescending { it.level }
    } else {
        this.filter { it.level in start..end }.sortedByDescending { it.level }
    }
}