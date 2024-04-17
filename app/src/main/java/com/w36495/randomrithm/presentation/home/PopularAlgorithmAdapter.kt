package com.w36495.randomrithm.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.w36495.randomrithm.databinding.ItemPopularAlgorithmBinding
import com.w36495.randomrithm.domain.entity.Tag

class PopularAlgorithmAdapter : RecyclerView.Adapter<PopularAlgorithmViewHolder>() {
    private lateinit var popularAlgorithmClickListener: PopularAlgorithmClickListener
    private var popularTags = emptyList<Tag>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularAlgorithmViewHolder {
        val view = ItemPopularAlgorithmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularAlgorithmViewHolder(view)
    }

    override fun getItemCount(): Int = popularTags.size

    override fun onBindViewHolder(holder: PopularAlgorithmViewHolder, position: Int) {
        holder.bind(popularTags[position])

        holder.onClickPopularAlgorithm = {
            popularAlgorithmClickListener.onClickPopularAlgorithm(popularTags[position])
        }
    }

    fun setupPopularTags(tags: List<Tag>) {
        this.popularTags = tags
        notifyDataSetChanged()
    }
}