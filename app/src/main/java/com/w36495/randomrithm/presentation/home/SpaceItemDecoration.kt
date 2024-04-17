package com.w36495.randomrithm.presentation.home

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.w36495.randomrithm.utils.dp

class SpaceItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val childCount = (parent.adapter as PopularAlgorithmAdapter).itemCount
        val position = parent.getChildAdapterPosition(view)

        if (position < childCount) {
            outRect.right = space.dp
        }
    }
}