package com.w36495.randomrithm.presentation.problem

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.android.material.chip.Chip
import com.w36495.randomrithm.R
import com.w36495.randomrithm.databinding.FragmentProblemBinding
import com.w36495.randomrithm.domain.entity.Problem
import com.w36495.randomrithm.domain.entity.ProblemType
import com.w36495.randomrithm.domain.entity.Tag
import com.w36495.randomrithm.domain.entity.TagType
import com.w36495.randomrithm.utils.putProblemType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProblemFragment : Fragment() {

    private var _binding: FragmentProblemBinding? = null
    private val binding: FragmentProblemBinding get() = _binding!!
    private val problemViewModel: ProblemViewModel by viewModels()
    private val navController by lazy { binding.root.findNavController() }

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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtonClickEvent()
        setupToolbarNavigation()

        levels = resources.getStringArray(R.array.levelList)
        levelBackgroundColors = resources.getIntArray(R.array.levelColorList)

        arguments?.let { bundle ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getSerializable(ARGUMENT_TAG, ProblemType::class.java)?.let { type ->
                    problemViewModel.initCurrentProblemType(type)
                }

            } else {
                val type = bundle.getSerializable(ARGUMENT_TAG) as? ProblemType
                type?.let { problemViewModel.initCurrentProblemType(it) }
            }
        }

        problemViewModel.problem.observe(viewLifecycleOwner) {
            showRandomProblem(it)
        }

        problemViewModel.problems.observe(viewLifecycleOwner) {
            problemViewModel.getProblem(it)
        }

        problemViewModel.problemType.observe(viewLifecycleOwner) { problemType ->
            val user = problemViewModel.getUserInfo()

            user?.let {
                problemViewModel.getSolvableProblems(it.id, problemType)
            } ?: problemViewModel.getProblems(problemType)
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

        problemViewModel.error.observe(viewLifecycleOwner) {
            if (!it.equals("")) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
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

            problemViewModel.getNextProblem()
        }

        binding.btnMoveProblem.setOnClickListener {
            problemViewModel.problem.value?.let { problem ->
                openProblemFromWebBrowser(problem.id)
            }
        }
    }

    private fun setupToolbarNavigation() {
        binding.layoutToolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
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

                this.setOnClickListener { showChangeProblemDialog(tag) }
            }
            binding.layoutChip.addView(chip)
        }
    }

    private fun showChangeProblemDialog(tag: Tag) {
        AlertDialog.Builder(requireContext())
            .apply {
                setTitle(getString(R.string.dialog_title_change_problem, tag.name))
                setPositiveButton(getString(R.string.dialog_btn_okay)) { dialog, _ ->
                    problemViewModel.saveCurrentProblem()

                    navController.navigate(
                        resId = R.id.action_problemFragment_to_problemFragment,
                        args = Bundle().putProblemType(TagType(tag = tag.key))
                    )

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
        const val ARGUMENT_TAG: String = "problemType"
        const val TAG: String = "ProblemFragment"
    }
}