package com.w36495.randomrithm.utils

import android.os.Bundle
import com.w36495.randomrithm.data.entity.LevelDTO

fun List<LevelDTO>.sortedByLevel(start: Int, end: Int): List<LevelDTO> {
    return if (start == 0 && end == 0) {
        this.filter { it.level == 0 }.sortedByDescending { it.level }
    } else {
        this.filter { it.level in start..end }.sortedByDescending { it.level }
    }
}

fun <T> Bundle.putValue(tag: String, value: T): Bundle {
    return this.apply {
        if (value is Int) putInt(tag, value)
        else if (value is String) putString(tag, value)
    }
}