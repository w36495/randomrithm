package com.w36495.randomrithm.ui.level

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.w36495.randomrithm.R
import com.w36495.randomrithm.data.datasource.LevelRemoteDataSource
import com.w36495.randomrithm.data.remote.RetrofitClient
import com.w36495.randomrithm.databinding.FragmentLevelBinding
import com.w36495.randomrithm.domain.repository.LevelRepositoryImpl
import com.w36495.randomrithm.domain.usecase.GetLevelsUseCase
import com.w36495.randomrithm.ui.viewmodel.LevelViewModelFactory

class LevelFragment : Fragment() {

    private var _binding: FragmentLevelBinding? = null
    private val binding: FragmentLevelBinding get() = _binding!!

    private lateinit var viewModelFactory: LevelViewModelFactory
    private lateinit var viewModel: LevelViewModel

    private lateinit var levelListAdapter: LevelListAdapter

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
        setupTabLayout()
        setupListView()

        viewModel.menu.observe(requireActivity()) {
            viewModel.getLevels(it)
            binding.layoutTab.getTabAt(it)?.select()
        }
    }

    private fun setupListView() {
        levelListAdapter = LevelListAdapter().apply {
            setLevelItemClickListener(this@LevelFragment)
        }

        binding.lvLevels.adapter = levelListAdapter
    }

    private fun setupTabLayout() {
        binding.layoutTab.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    viewModel.changeMenu(it.position)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) { }
            override fun onTabReselected(tab: TabLayout.Tab?) { }
        })
    }

    private fun setupViewModel() {
        viewModelFactory = LevelViewModelFactory(GetLevelsUseCase(LevelRepositoryImpl(LevelRemoteDataSource(RetrofitClient.levelAPI))))
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[LevelViewModel::class.java]
    }

    private fun initSelectLevel() {
        parentFragmentManager.beginTransaction()
            .addToBackStack(LevelListFragment.TAG)
            .setReorderingAllowed(true)
            .replace(R.id.container_level_fragment, LevelListFragment())
            .commit()

        viewModel.getLevels(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}