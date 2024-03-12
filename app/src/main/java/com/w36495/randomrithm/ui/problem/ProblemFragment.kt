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
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.w36495.randomrithm.R
import com.w36495.randomrithm.databinding.FragmentProblemBinding
import com.w36495.randomrithm.domain.entity.Problem
import com.w36495.randomrithm.domain.entity.Tag
import com.w36495.randomrithm.utils.putValue
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProblemFragment : Fragment() {

    private var _binding: FragmentProblemBinding? = null
    private val binding: FragmentProblemBinding get() = _binding!!
    private val problemViewModel: ProblemViewModel by viewModels()

    private var currentTag: String? = null
    private var currentLevel: Int? = null
    private var currentSource: String? = null

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
            currentTag = it.getString(INSTANCE_TAG)
            currentLevel = it.getInt(INSTANCE_LEVEL)
            currentSource = it.getString(INSTANCE_SOURCE)
        }

        currentTag?.let { tag ->
            currentLevel?.let { level ->
                if (level == All_LEVEL) { problemViewModel.getProblemsByTag(tag) }
                else problemViewModel.getProblemByTagAndLevel(tag, level)
            }
        } ?: currentLevel?.let { level ->
            problemViewModel.getProblemsByLevel(level)
        }

        currentSource?.let { source ->
            problemViewModel.getProblemsBySourceOfProblem(source)
        }

        problemViewModel.problems.observe(viewLifecycleOwner) {
            currentProblems = it.toList()
            count = 0

            currentTag?.let { tag ->
                currentLevel?.let { level ->
                    if (level == All_LEVEL) {
                        getRandomProblems { problemViewModel.getProblemsByTag(tag) }
                    } else {
                        getRandomProblems { problemViewModel.getProblemByTagAndLevel(tag, level) }
                    }
                }
            } ?: currentLevel?.let { level ->
                getRandomProblems { problemViewModel.getProblemsByLevel(level) }
            }

            currentSource?.let { source ->
                getRandomProblems { problemViewModel.getProblemsBySourceOfProblem(source) }
            }
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

        viewLifecycleOwner.lifecycleScope.launch {
            problemViewModel.tagState.collectLatest { state ->
                binding.iBtnAlgorithm.tag = state

                if (state) {
                    binding.layoutChip.visibility = View.VISIBLE
                    binding.iBtnAlgorithm.setImageDrawable(resources.getDrawable(R.drawable.ic_arrow_drop_down_24))
                } else {
                    binding.layoutChip.visibility = View.INVISIBLE
                    binding.iBtnAlgorithm.setImageDrawable(resources.getDrawable(R.drawable.ic_arrow_drop_up_24))
                }
            }
        }

        binding.iBtnAlgorithm.setOnClickListener {
            val currentState = binding.iBtnAlgorithm.tag
            binding.iBtnAlgorithm.tag = if (currentState == "true") "false" else "true"

            changeTagImageButton()
        }
    }

    private fun changeTagImageButton() {
        val currentState = binding.iBtnAlgorithm.tag

        if (currentState == "true") {
            binding.iBtnAlgorithm.setImageDrawable(resources.getDrawable(R.drawable.ic_arrow_drop_down_24))
            binding.layoutChip.visibility = View.VISIBLE
        } else {
            binding.iBtnAlgorithm.setImageDrawable(resources.getDrawable(R.drawable.ic_arrow_drop_up_24))
            binding.layoutChip.visibility = View.INVISIBLE
        }
    }

    private fun setupButtonClickEvent() {
        binding.btnNextProblem.setOnClickListener {
            if (problemViewModel.hasSavedProblem()) problemViewModel.clearSavedProblem()

            currentTag?.let { tag ->
                currentLevel?.let {  level ->
                    if (level == All_LEVEL) {
                        getRandomProblems { problemViewModel.getProblemsByTag(tag) }
                    } else {
                        getRandomProblems { problemViewModel.getProblemByTagAndLevel(tag, level) }
                    }
                }
            } ?: currentLevel?.let { level ->
                getRandomProblems { problemViewModel.getProblemsByLevel(level) }
            }

            currentSource?.let { source ->
                getRandomProblems { problemViewModel.getProblemsBySourceOfProblem(source) }
            }
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

    private fun getRandomProblems(block: () -> Unit) {
        if (count >= currentProblems.size) block()
        else if (problemViewModel.hasSavedProblem()) showRandomProblem(problemViewModel.getSavedProblem())
        else showRandomProblem(currentProblems[count++])
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
                    if (problemViewModel.hasSavedProblem()) {
                        problemViewModel.saveCurrentProblem(problemViewModel.getSavedProblem())
                    } else {
                        problemViewModel.saveCurrentProblem(currentProblems[count-1])
                    }

                    parentFragmentManager.beginTransaction()
                        .addToBackStack(TAG)
                        .setReorderingAllowed(true)
                        .replace(R.id.container_fragment, newInstance(INSTANCE_TAG, tag.key, INSTANCE_LEVEL, All_LEVEL))
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
        private const val All_LEVEL: Int = -1
        const val TAG: String = "ProblemFragment"
        const val INSTANCE_TAG: String = "tag"
        const val INSTANCE_LEVEL: String = "level"
        const val INSTANCE_SOURCE: String = "source"

        fun <T> newInstance(tag: String, value: T): Fragment {
            return ProblemFragment().apply {
                arguments = Bundle().putValue(tag, value)
            }
        }

        fun <T> newInstance(tag: String, value1: T, tag2: String, value2: T): Fragment {
            return ProblemFragment().apply {
                arguments = Bundle().apply {
                    putValue(tag, value1)
                    putValue(tag2, value2)
                }
            }
        }
    }
}