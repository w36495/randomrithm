package com.w36495.randomrithm.ui.level

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.w36495.randomrithm.R
import com.w36495.randomrithm.data.datasource.LevelRemoteDataSource
import com.w36495.randomrithm.data.remote.RetrofitClient
import com.w36495.randomrithm.databinding.FragmentLevelListBinding
import com.w36495.randomrithm.data.repository.LevelRepositoryImpl
import com.w36495.randomrithm.domain.usecase.GetLevelsUseCase
import com.w36495.randomrithm.ui.problem.ProblemFragment
import com.w36495.randomrithm.ui.viewmodel.LevelViewModelFactory

class LevelListFragment : Fragment(), LevelItemClickListener {
    private var _binding: FragmentLevelListBinding? = null
    private val binding: FragmentLevelListBinding get() = _binding!!

    private lateinit var viewModel: LevelViewModel
    private lateinit var levelViewModelFactory: LevelViewModelFactory
    private lateinit var levelListAdapter: LevelListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLevelListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupListView()

        viewModel.levels.observe(requireActivity()) {
            levelListAdapter.setLevelList(it)
        }
    }

    private fun setupViewModel() {
        levelViewModelFactory = LevelViewModelFactory(GetLevelsUseCase(LevelRepositoryImpl(LevelRemoteDataSource(RetrofitClient.levelAPI))))
        viewModel = ViewModelProvider(requireActivity(), levelViewModelFactory)[LevelViewModel::class.java]
    }

    private fun setupListView() {
        levelListAdapter = LevelListAdapter().apply {
            setLevelItemClickListener(this@LevelListFragment)
        }

        binding.containerListview.adapter = levelListAdapter
    }

    override fun onClickLevelItem(level: Int) {
        parentFragment?.let {
            it.parentFragmentManager.beginTransaction()
                .addToBackStack(ProblemFragment.TAG)
                .setReorderingAllowed(true)
                .replace(R.id.container_fragment, ProblemFragment.newInstance(level))
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG: String = "LevelListFragment"

        fun newInstance(level: Int): Fragment {
            val fragment = LevelListFragment()
            fragment.arguments = Bundle().apply {
                putInt("level", level)
            }

            return fragment
        }
    }
}