package com.w36495.randomrithm.presentation.level

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.w36495.randomrithm.R
import com.w36495.randomrithm.databinding.FragmentLevelBinding
import com.w36495.randomrithm.presentation.tag.TagFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LevelFragment : Fragment() {

    private var _binding: FragmentLevelBinding? = null
    private val binding: FragmentLevelBinding get() = _binding!!

    private val viewModel: LevelViewModel by activityViewModels()
    private lateinit var viewPagerAdapter: LevelViewPagerAdapter
    private val navController by lazy { binding.root.findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hasHomeParentView = arguments?.takeIf {
            it.containsKey(TagFragment.ARGUMENT_PARENT_VIEW_HOME)
        }?.getBoolean(TagFragment.ARGUMENT_PARENT_VIEW_HOME)

        if (hasHomeParentView == true){
            binding.layoutToolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_24)
        }

        binding.layoutToolbar.setNavigationOnClickListener {
            navController.popBackStack()
        }

        setupViewPager()
        setupTabLayout()
    }

    private fun setupViewPager() {
        viewPagerAdapter = LevelViewPagerAdapter(this)

        binding.containerViewpager.adapter = viewPagerAdapter
        binding.containerViewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                viewModel.getLevels(position)
            }
        })
    }

    private fun setupTabLayout() {
        TabLayoutMediator(binding.layoutTab, binding.containerViewpager) { tab, position ->
            tab.text = resources.getStringArray(R.array.levelForTabItem)[position]
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}