package com.w36495.randomrithm.presentation.tag

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.w36495.randomrithm.databinding.ItemAlgorhtimBinding
import com.w36495.randomrithm.domain.entity.Tag

class TagAdapter : RecyclerView.Adapter<TagViewHolder>() {
    private lateinit var tagClickListener: TagClickListener

    private var tags: List<Tag> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        return TagViewHolder(ItemAlgorhtimBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = tags.size

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(tags[position])

        holder.onClickItem = {
            tagClickListener.onClickTagItem(tags[position].key)
        }
    }

    fun setList(list: List<Tag>) {
        tags = list
        notifyDataSetChanged()
    }

    fun setTagClickListener(listener: TagClickListener) {
        tagClickListener = listener
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