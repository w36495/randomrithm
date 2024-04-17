package com.w36495.randomrithm.presentation.home

import androidx.recyclerview.widget.RecyclerView
import com.w36495.randomrithm.databinding.ItemPopularAlgorithmBinding
import com.w36495.randomrithm.domain.entity.Tag

class PopularAlgorithmViewHolder(private val binding: ItemPopularAlgorithmBinding) : RecyclerView.ViewHolder(binding.root) {
    var onClickPopularAlgorithm: ((PopularAlgorithmViewHolder) -> Unit)? = null

    fun bind(tag: Tag) {
        binding.tvTagName.text = tag.name

        itemView.setOnClickListener {
            onClickPopularAlgorithm?.let {
                it(this@PopularAlgorithmViewHolder)
            }
        }
    }
}