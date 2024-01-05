package com.w36495.randomrithm.ui.level

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.w36495.randomrithm.R
import com.w36495.randomrithm.data.entity.LevelDTO
import com.w36495.randomrithm.databinding.ItemLevelBinding

class LevelListAdapter : BaseAdapter() {

    private var levelList: List<LevelDTO> = arrayListOf()
    private lateinit var levelItemClickListener: LevelItemClickListener

    override fun getCount(): Int = levelList.size

    override fun getItem(position: Int): Any {
        return levelList[position]
    }

    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var convertView: View? = view
        var binding: ItemLevelBinding
        var holder: LevelListViewHolder

        if (convertView == null) {
            binding = ItemLevelBinding.inflate(LayoutInflater.from(parent!!.context), parent, false)
            convertView = binding.root

            holder = LevelListViewHolder(binding)
            holder.bind(levelList[position])

            holder.onClickItem = {
                levelItemClickListener.onClickLevelItem(levelList[position].level)
            }
        }

        return convertView
    }

    fun setLevelList(list: ArrayList<LevelDTO>) {
        levelList = list
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