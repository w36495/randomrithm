package com.w36495.randomrithm.ui.level

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.w36495.randomrithm.R
import com.w36495.randomrithm.data.entity.Level
import com.w36495.randomrithm.databinding.ItemCategoryBinding

class LevelListAdapter : BaseAdapter() {

    private var levelList: List<Level> = arrayListOf()

    override fun getCount(): Int = levelList.size

    override fun getItem(position: Int): Any {
        return levelList[position]
    }

    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var convertView: View? = view
        var binding: ItemCategoryBinding
        var holder: LevelListViewHolder

        if (convertView == null) {
            binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent!!.context), parent, false)
            convertView = binding.root

            holder = LevelListViewHolder(binding)
            holder.bind(levelList[position])
        }

        return convertView
    }

    fun setLevelList(list: ArrayList<Level>) {
        levelList = list
        notifyDataSetChanged()
    }

}

class LevelListViewHolder(private val binding: ItemCategoryBinding) {
    fun bind(level: Level) {
        binding.tvCategory.text = "${binding.root.resources.getStringArray(R.array.levelList)[level.level]} (${level.count})"
    }
}