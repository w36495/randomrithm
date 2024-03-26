package com.w36495.randomrithm.presentation.tag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.w36495.randomrithm.R
import com.w36495.randomrithm.databinding.FragmentAlgorithmBinding
import com.w36495.randomrithm.domain.entity.ProblemType
import com.w36495.randomrithm.presentation.problem.ProblemFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TagFragment : Fragment(), TagClickListener, LevelSelectionClickListener {

    private var _binding: FragmentAlgorithmBinding? = null
    private val binding: FragmentAlgorithmBinding get() = _binding!!
    private val tagViewModel: TagViewModel by viewModels()

    private lateinit var tagAdapter: TagAdapter

    private var currentTag: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlgorithmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        tagViewModel.getTags()
        tagViewModel.tags.observe(requireActivity()) { tags ->
            tagAdapter.setList(tags)
        }

        tagViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.layoutProgress.visibility = View.VISIBLE
            } else {
                binding.layoutProgress.visibility = View.INVISIBLE
            }
        }

        tagViewModel.problemCountOfTag.observe(viewLifecycleOwner) {
            currentTag?.let { tag ->
                showLevelSelectionDialog(tag, it)
            }
        }
    }

    override fun onClickTagItem(tagKey: String) {
        currentTag = tagKey
        tagViewModel.hasProblemOfTag(tagKey)
    }

    override fun onClickLevel(level: Int, tag: String) {
        parentFragmentManager.beginTransaction()
            .addToBackStack(ProblemFragment.TAG)
            .setReorderingAllowed(true)
            .replace(
                R.id.container_fragment,
                ProblemFragment.newInstance(ProblemType(tag = tag, level = level))
            )
            .commit()
    }

    private fun setupRecyclerView() {
        tagAdapter = TagAdapter()
        binding.containerRecyclerview.apply {
            adapter = tagAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        tagAdapter.setTagClickListener(this)
    }

    private fun showLevelSelectionDialog(tag: String, possibleSelectionLevel: List<Boolean>) {
        LevelSelectionDialog().apply {
            setLevelSelectionClickListener(this@TagFragment)
            arguments = Bundle().apply {
                putString("tag", tag)
                putBooleanArray("levels", possibleSelectionLevel.toBooleanArray())
            }
        }.show(parentFragmentManager, LevelSelectionDialog.TAG)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG: String = "TagFragment"
    }
}