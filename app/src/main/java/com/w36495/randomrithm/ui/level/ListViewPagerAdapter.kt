package com.w36495.randomrithm.ui.level

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ListViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 7

    override fun createFragment(position: Int): Fragment {
        return LevelListFragment(position)
    }
}