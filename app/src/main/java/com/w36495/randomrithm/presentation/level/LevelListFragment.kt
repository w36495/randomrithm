package com.w36495.randomrithm.presentation.level

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.w36495.randomrithm.R
import com.w36495.randomrithm.databinding.FragmentLevelListBinding
import com.w36495.randomrithm.domain.entity.DetailLevelType
import com.w36495.randomrithm.utils.putProblemType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LevelListFragment : Fragment(), LevelItemClickListener {
    private var _binding: FragmentLevelListBinding? = null
    private val binding: FragmentLevelListBinding get() = _binding!!

    private val viewModel: LevelViewModel by activityViewModels()
    private val navController by lazy { findNavController() }
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

        setupListView()

        viewModel.levels.observe(requireActivity()) {
            levelListAdapter.setLevelList(it)
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.layoutProgress.visibility = View.VISIBLE
            } else {
                binding.layoutProgress.visibility = View.INVISIBLE
            }
        }
    }

    private fun setupListView() {
        levelListAdapter = LevelListAdapter().apply {
            setLevelItemClickListener(this@LevelListFragment)
        }

        binding.containerListview.adapter = levelListAdapter
    }

    override fun onClickLevelItem(level: Int) {
        navController.navigate(
            resId = R.id.action_levelFragment_to_problemFragment,
            args = Bundle().putProblemType(DetailLevelType(level = level))
        )
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