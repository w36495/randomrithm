package com.w36495.randomrithm.ui.level

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.w36495.randomrithm.R
import com.w36495.randomrithm.data.entity.LevelDTO
import com.w36495.randomrithm.databinding.ItemLevelBinding

class LevelListAdapter : BaseAdapter() {
    private var levelList = emptyList<LevelDTO>()
    private lateinit var levelItemClickListener: LevelItemClickListener

    override fun getCount(): Int = levelList.size

    override fun getItem(position: Int): Any = levelList[position]

    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var convertView: View? = view
        var binding: ItemLevelBinding
        var holder: LevelListViewHolder

        if (convertView == null) {
            binding = ItemLevelBinding.inflate(LayoutInflater.from(parent!!.context), parent, false)
            convertView = binding.root

            holder = LevelListViewHolder(binding)
            convertView.tag = holder
        } else {
            binding = ItemLevelBinding.bind(convertView)
            holder = convertView.tag as LevelListViewHolder
        }

        holder.bind(levelList[position])

        holder.onClickItem = {
            levelItemClickListener.onClickLevelItem(levelList[position].level)
        }

        return convertView
    }

    fun setLevelList(list: List<LevelDTO>) {
        levelList = list.toList()

        notifyDataSetChanged()
    }

    fun setLevelItemClickListener(listener: LevelItemClickListener) {
        levelItemClickListener = listener
    }
}

class LevelListViewHolder(private val binding: ItemLevelBinding) {
    var onClickItem: ((LevelListViewHolder) -> Unit)? = null

    fun bind(level: LevelDTO) {
        binding.tvCategory.text = "${binding.root.resources.getStringArray(R.array.levelList)[level.level]} (${level.count})"

        binding.root.setOnClickListener {
            onClickItem?.let { onClickItem ->
                onClickItem(this@LevelListViewHolder)
            }
        }
    }
}