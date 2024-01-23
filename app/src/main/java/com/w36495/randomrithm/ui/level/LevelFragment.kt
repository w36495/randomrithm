package com.w36495.randomrithm.ui.level

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.w36495.randomrithm.R
import com.w36495.randomrithm.data.datasource.LevelRemoteDataSource
import com.w36495.randomrithm.data.remote.RetrofitClient
import com.w36495.randomrithm.databinding.FragmentLevelBinding
import com.w36495.randomrithm.data.repository.LevelRepositoryImpl
import com.w36495.randomrithm.domain.usecase.GetLevelsUseCase
import com.w36495.randomrithm.ui.viewmodel.LevelViewModelFactory

class LevelFragment : Fragment() {

    private var _binding: FragmentLevelBinding? = null
    private val binding: FragmentLevelBinding get() = _binding!!

    private lateinit var viewModelFactory: LevelViewModelFactory
    private lateinit var viewModel: LevelViewModel

    private lateinit var viewPagerAdapter: LevelViewPagerAdapter

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

        setupViewModel()
        setupViewPager()
        setupTabLayout()
    }

    private fun setupViewModel() {
        viewModelFactory = LevelViewModelFactory(GetLevelsUseCase(LevelRepositoryImpl(LevelRemoteDataSource(RetrofitClient.levelAPI))))
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[LevelViewModel::class.java]
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