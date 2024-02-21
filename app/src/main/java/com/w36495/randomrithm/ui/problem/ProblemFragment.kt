package com.w36495.randomrithm.ui.problem

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.w36495.randomrithm.R
import com.w36495.randomrithm.databinding.FragmentProblemBinding
import com.w36495.randomrithm.domain.entity.Problem
import com.w36495.randomrithm.domain.entity.Tag
import com.w36495.randomrithm.utils.putValue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProblemFragment : Fragment() {

    private var _binding: FragmentProblemBinding? = null
    private val binding: FragmentProblemBinding get() = _binding!!
    private val problemViewModel: ProblemViewModel by viewModels()

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

        problemViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.layoutShimmer.startShimmer()
                binding.layoutShimmer.visibility = View.VISIBLE
            } else {
                binding.layoutShimmer.stopShimmer()
                binding.layoutShimmer.visibility = View.INVISIBLE
            }
        }
    }

    private fun setupButtonClickEvent() {
        binding.btnNextProblem.setOnClickListener {
            if (problemViewModel.hasSavedProblem()) problemViewModel.clearSavedProblem()

            currentLevel?.let { getRandomProblemByLevel(it, currentProblems) }
            currentTag?.let { getRandomProblemByTag(it, currentProblems) }
        }

        binding.btnMoveProblem.setOnClickListener {
            openProblemFromWebBrowser(currentProblems[count-1].id)
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
            else if (problemViewModel.hasSavedProblem()) showRandomProblem(problemViewModel.getSavedProblem())
            else showRandomProblem(currentProblems[count++])
        }
    }

    private fun getRandomProblemByTag(currentTag: String, currentProblems: List<Problem>) {
        if (currentProblems.isNotEmpty() && currentProblems.all { problem -> problem.tags.any { it.key == currentTag } }) {
            if (count >= currentProblems.size) problemViewModel.getProblemsByTag(currentTag)
            else if (problemViewModel.hasSavedProblem()) showRandomProblem(problemViewModel.getSavedProblem())
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

        chips.forEach {tag ->
            val chip = Chip(requireContext()).apply {
                text = tag.name
                setChipBackgroundColorResource(R.color.white)

                this.setOnClickListener {
                    showChangeProblemDialog(tag)
                }
            }
            binding.layoutChip.addView(chip)
        }
    }

    private fun showChangeProblemDialog(tag: Tag) {
        AlertDialog.Builder(requireContext())
            .apply {
                setTitle(getString(R.string.dialog_title_change_problem, tag.name))
                setPositiveButton(getString(R.string.dialog_btn_okay)) { dialog, _ ->
                    problemViewModel.saveCurrentProblem(currentProblems[count-1])

                    parentFragmentManager.beginTransaction()
                        .addToBackStack(TAG)
                        .setReorderingAllowed(true)
                        .replace(R.id.container_fragment, newInstance(INSTANCE_TAG, tag.key))
                        .commit()

                    dialog.dismiss()
                }
                setNeutralButton(getString(R.string.dialog_btn_cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
            }.show()
    }

    private fun openProblemFromWebBrowser(problemId: Int) {
        val webpage: Uri = Uri.parse(BASE_URL + problemId)
        val intent = Intent(Intent.ACTION_VIEW, webpage)

        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val BASE_URL: String = "https://www.acmicpc.net/problem/"
        const val TAG: String = "ProblemFragment"
        const val INSTANCE_TAG: String = "tag"
        const val INSTANCE_LEVEL: String = "level"

        fun <T> newInstance(tag: String, value: T): Fragment {
            return ProblemFragment().apply {
                arguments = Bundle().putValue(tag, value)
            }
        }
    }
}