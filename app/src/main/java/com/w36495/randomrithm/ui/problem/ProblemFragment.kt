package com.w36495.randomrithm.ui.problem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.w36495.randomrithm.R
import com.w36495.randomrithm.data.datasource.ProblemRemoteDataSource
import com.w36495.randomrithm.data.remote.RetrofitClient
import com.w36495.randomrithm.databinding.FragmentProblemBinding
import com.w36495.randomrithm.domain.repository.ProblemRepositoryImpl
import com.w36495.randomrithm.domain.usecase.GetProblemUseCase
import com.w36495.randomrithm.ui.ProblemOfAlgorithmClickListener
import com.w36495.randomrithm.ui.ProblemOfLevelClickListener
import com.w36495.randomrithm.ui.viewmodel.ProblemViewModel
import com.w36495.randomrithm.ui.viewmodel.ProblemViewModelFactory

class ProblemFragment : Fragment() {

    private var _binding: FragmentProblemBinding? = null
    private val binding: FragmentProblemBinding get() = _binding!!

    private lateinit var problemViewModel: ProblemViewModel
    private lateinit var problemViewModelFactory: ProblemViewModelFactory
    private lateinit var problemOfAlgorithmClickListener: ProblemOfAlgorithmClickListener
    private lateinit var problemOfLevelClickListener: ProblemOfLevelClickListener

    private var currentProblemId: Int? = null
    private var currentTag: String? = null
    private var currentLevel: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProblemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupButtons()
        setupToolbarNavigation()

        val levels = resources.getStringArray(R.array.levelList)
        val levelBackgroundColors = resources.getIntArray(R.array.levelColorList)

        arguments?.let {
            if (it.getString("tag") != null) {
                currentTag = it.getString("tag")
                currentProblemId = it.getInt("problemId")
            } else if(it.getInt("level") != null) {
                currentLevel = it.getInt("level")
            }
        }

        currentProblemId?.let {
            problemViewModel.getProblem(it)
        }

        problemViewModel.problem.observe(requireActivity()) { problem ->
            binding.tvLevel.text = levels[problem.level.toInt()]
            binding.tvLevel.setBackgroundColor(levelBackgroundColors[problem.level.toInt()])
            binding.tvTitle.text = problem.title
            binding.tvId.text = problem.id.toString()
            showAlgorithmChips(problem.algorithms)
        }
    }

    private fun setupViewModel() {
        problemViewModelFactory = ProblemViewModelFactory(GetProblemUseCase(ProblemRepositoryImpl(ProblemRemoteDataSource(RetrofitClient.problemAPI))))
        problemViewModel = ViewModelProvider(requireActivity(), problemViewModelFactory)[ProblemViewModel::class.java]
    }

    private fun setupButtons() {
        binding.btnNextProblem.setOnClickListener {
            currentTag?.let {
                problemOfAlgorithmClickListener.onClickNextProblem(it)
            }
        }
    }

    private fun setupToolbarNavigation() {
        binding.layoutToolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun showAlgorithmChips(chips: List<String>) {
        chips.forEach {
            val chip = Chip(requireContext()).apply {
                text = it
            }
            binding.layoutChip.addView(chip)
        }
    }

    fun setProblemOfAlgorithmCLickListener(listener: ProblemOfAlgorithmClickListener) {
        problemOfAlgorithmClickListener = listener
    }

    fun setProblemOfLevelClickListener(listener: ProblemOfLevelClickListener) {
        problemOfLevelClickListener = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}