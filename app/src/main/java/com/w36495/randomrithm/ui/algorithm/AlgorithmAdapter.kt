package com.w36495.randomrithm.ui.algorithm

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.w36495.randomrithm.data.entity.AlgorithmItem
import com.w36495.randomrithm.databinding.ItemAlgorhtimBinding

class AlgorithmAdapter : RecyclerView.Adapter<AlgorithmViewHolder>() {

    private var categoryList: List<AlgorithmItem> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlgorithmViewHolder {
        return AlgorithmViewHolder(ItemAlgorhtimBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: AlgorithmViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    fun setList(list: List<AlgorithmItem>) {
        categoryList = list
        notifyDataSetChanged()
    }
}

class AlgorithmViewHolder(
    private val binding: ItemAlgorhtimBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(algorithm: AlgorithmItem) {
        binding.tvCategory.text = "${algorithm.displayNames[0].name} (${algorithm.problemCount})"
    }
}