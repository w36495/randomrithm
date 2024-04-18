package com.w36495.randomrithm.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.w36495.randomrithm.data.entity.LevelDTO
import com.w36495.randomrithm.data.entity.ProblemDTO
import com.w36495.randomrithm.domain.entity.Problem
import com.w36495.randomrithm.domain.entity.ProblemType
import com.w36495.randomrithm.domain.entity.Tag
import com.w36495.randomrithm.presentation.problem.ProblemFragment

fun List<LevelDTO>.sortedByLevel(start: Int, end: Int): List<LevelDTO> {
    return if (start == 0 && end == 0) {
        this.filter { it.level == 0 }.sortedByDescending { it.level }
    } else {
        this.filter { it.level in start..end }.sortedByDescending { it.level }
    }
}

fun ProblemDTO.toProblems(): List<Problem> {
    val problems = mutableListOf<Problem>()
    this.items.forEach {
        val tags = mutableListOf<Tag>()
        it.tags.forEach { tag ->
            tags.add(Tag(tag.bojTagId, tag.key, tag.displayNames[0].name, tag.problemCount))
        }

        problems.add(Problem(it.problemId, it.level.toString(), it.titleKo, tags.toList()))
    }

    return problems.toList()
}

fun Bundle.putProblemType(problemType: ProblemType): Bundle {
    return this.apply {
        putSerializable(ProblemFragment.ARGUMENT_TAG, problemType)
    }
}

fun Context.showShortToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

inline val Int.dp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics
    ).toInt()

fun Context.dialogFragmentResize(dialogFragment: DialogFragment, width: Float) {
    val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

    if (Build.VERSION.SDK_INT < 30) {
        val dialogParams = dialogFragment.dialog?.window?.attributes
        val display = windowManager.defaultDisplay
        val size = Point()

        display.getSize(size)

        dialogParams?.width = (size.x * width).toInt()
        dialogFragment.dialog?.window?.attributes = dialogParams

    } else {
        val rect = windowManager.currentWindowMetrics.bounds
        val dialogParams = dialogFragment.dialog?.window?.attributes

        dialogParams?.width = (rect.width() * width).toInt()
        dialogFragment.dialog?.window?.attributes = dialogParams
    }
}