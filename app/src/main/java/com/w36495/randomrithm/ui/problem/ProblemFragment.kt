package com.w36495.randomrithm.ui.problem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.w36495.randomrithm.R
import com.w36495.randomrithm.data.datasource.ProblemRemoteDataSource
import com.w36495.randomrithm.data.remote.RetrofitClient
import com.w36495.randomrithm.databinding.FragmentProblemBinding
import com.w36495.randomrithm.domain.entity.Problem
import com.w36495.randomrithm.domain.entity.Tag
import com.w36495.randomrithm.data.repository.ProblemRepositoryImpl
import com.w36495.randomrithm.domain.usecase.GetProblemsByLevelUseCase
import com.w36495.randomrithm.domain.usecase.GetProblemsByTagUseCase
import com.w36495.randomrithm.ui.viewmodel.ProblemViewModelFactory

class ProblemFragment : Fragment() {

    private var _binding: FragmentProblemBinding? = null
    private val binding: FragmentProblemBinding get() = _binding!!

    private lateinit var problemViewModel: ProblemViewModel
    private lateinit var problemViewModelFactory: ProblemViewModelFactory

    private var currentTag: String? = null
    private var currentLevel: Int? = null
    private var currentProblems = emptyList<Problem>()
    private var count: Int = 0

    private lateinit var levels: Array<String>
    private lateinit var levelBackgroundColors: IntArray

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
        setupButtonClickEvent()
        setupToolbarNavigation()

        levels = resources.getStringArray(R.array.levelList)
        levelBackgroundColors = resources.getIntArray(R.array.levelColorList)

        arguments?.let {
            if (it.getString("tag") != null) {
                currentTag = it.getString("tag")
                problemViewModel.getProblemsByTag(currentTag!!)
            } else if (it.getInt("level") != null) {
                currentLevel = it.getInt("level")
                problemViewModel.getProblemsByLevel(currentLevel!!)
            }
        }

        problemViewModel.problems.observe(viewLifecycleOwner) {
            currentProblems = it.toList()
            count = 0

            currentLevel?.let { getRandomProblemByLevel(it, currentProblems) }
            currentTag?.let { getRandomProblemByTag(it, currentProblems) }
        }
    }

    private fun setupViewModel() {
        problemViewModelFactory = ProblemViewModelFactory(
            GetProblemsByLevelUseCase(ProblemRepositoryImpl(ProblemRemoteDataSource(RetrofitClient.problemAPI))),
            GetProblemsByTagUseCase(ProblemRepositoryImpl(ProblemRemoteDataSource(RetrofitClient.problemAPI)))
        )
        problemViewModel = ViewModelProvider(this, problemViewModelFactory)[ProblemViewModel::class.java]
    }

    private fun setupButtonClickEvent() {
        binding.btnNextProblem.setOnClickListener {
            currentLevel?.let { getRandomProblemByLevel(it, currentProblems) }
            currentTag?.let { getRandomProblemByTag(it, currentProblems) }
        }
    }

    private fun setupToolbarNavigation() {
        binding.layoutToolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun getRandomProblemByLevel(currentLevel: Int, currentProblems: List<Problem>) {
        if (currentProblems.isNotEmpty() && currentProblems.all { it.level.toInt() == currentLevel }) {
            if (count >= currentProblems.size) problemViewModel.getProblemsByLevel(currentLevel)
            else showRandomProblem(currentProblems[count++])
        }
    }

    private fun getRandomProblemByTag(currentTag: String, currentProblems: List<Problem>) {
        if (currentProblems.isNotEmpty() && currentProblems.all { problem -> problem.tags.any { it.key == currentTag } }) {
            if (count >= currentProblems.size) problemViewModel.getProblemsByTag(currentTag)
            else showRandomProblem(currentProblems[count++])
        }
    }

    private fun showRandomProblem(randomProblem: Problem) {
        randomProblem.run {
            binding.tvTitle.text = title
            binding.tvId.text = id.toString()
            binding.tvLevel.text = levels[level.toInt()]
            binding.tvLevel.setBackgroundColor(levelBackgroundColors[level.toInt()])
            showAlgorithmChips(tags)
        }
    }

    private fun showAlgorithmChips(chips: List<Tag>) {
        binding.layoutChip.removeAllViews()

        chips.forEach {
            val chip = Chip(requireContext()).apply {
                text = it.name
            }
            binding.layoutChip.addView(chip)
        }
    }

    private fun showChangeProblemDialog(tag: Tag) {
        AlertDialog.Builder(requireContext())
            .apply {
                setTitle(getString(R.string.dialog_title_change_problem, tag.name))
                setPositiveButton(getString(R.string.dialog_btn_okay)) { dialog, _ ->
                    parentFragmentManager.beginTransaction()
                        .addToBackStack(TAG)
                        .setReorderingAllowed(true)
                        .replace(R.id.container_fragment, newInstance(tag.key))
                        .commit()

                    dialog.dismiss()
                }
                setNeutralButton(getString(R.string.dialog_btn_cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
            }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG: String = "ProblemFragment"
        fun newInstance(level: Int): Fragment {
            val problemFragment = ProblemFragment().apply {
                arguments = Bundle().apply {
                    putInt("level", level)
                }
            }

            return problemFragment
        }

        fun newInstance(tag: String): Fragment {
            val fragment = ProblemFragment().apply {
                arguments = Bundle().apply {
                    putString("tag", tag)
                }
            }

            return fragment
        }
    }
}