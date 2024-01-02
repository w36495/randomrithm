package com.w36495.randomrithm.ui.algorithm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.w36495.randomrithm.databinding.ItemAlgorhtimBinding
import com.w36495.randomrithm.domain.entity.Tag
import com.w36495.randomrithm.ui.AlgorithmItemClickListener

class TagAdapter : RecyclerView.Adapter<TagViewHolder>() {
    private lateinit var algorithmItemClickListener: AlgorithmItemClickListener

    private var tags: List<Tag> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        return TagViewHolder(ItemAlgorhtimBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = tags.size

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(tags[position])

        holder.onClickItem = {
            algorithmItemClickListener.onClickAlgorithmItem(tags[position].id.toString())
        }
    }

    fun setList(list: List<Tag>) {
        tags = list
        notifyDataSetChanged()
    }

    fun setAlgorithmItemClickListener(listener: AlgorithmItemClickListener) {
        algorithmItemClickListener = listener
    }
}

class TagViewHolder(
    private val binding: ItemAlgorhtimBinding
) : RecyclerView.ViewHolder(binding.root) {

    var onClickItem: ((RecyclerView.ViewHolder) -> Unit)? = null
    fun bind(tag: Tag) {
        binding.tvCategory.text = "${tag.name} (${tag.problemCount})"

        itemView.setOnClickListener {
            onClickItem?.let { onClickItem ->
                onClickItem(this@TagViewHolder)
            }
        }
    }
}