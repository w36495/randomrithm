package com.w36495.randomrithm.presentation.tag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.w36495.randomrithm.R
import com.w36495.randomrithm.databinding.FragmentAlgorithmBinding
import com.w36495.randomrithm.domain.entity.TagAndLevelType
import com.w36495.randomrithm.utils.putProblemType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TagFragment : Fragment(), TagClickListener, LevelSelectionClickListener {

    private var _binding: FragmentAlgorithmBinding? = null
    private val binding: FragmentAlgorithmBinding get() = _binding!!
    private val tagViewModel: TagViewModel by viewModels()
    private val navController by lazy { binding.root.findNavController() }

    private lateinit var tagAdapter: TagAdapter
    private var currentTag: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlgorithmBinding.inflate(inflater, container, false)

        val parentView = arguments?.takeIf { it.containsKey(ARGUMENT_PARENT_VIEW_HOME) }?.getBoolean(ARGUMENT_PARENT_VIEW_HOME)
        if (parentView != null){
            binding.layoutToolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_24)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        subscribeUi()

        binding.layoutToolbar.setNavigationOnClickListener {
            navController.popBackStack()
        }
    }

    private fun subscribeUi() {
        with (tagViewModel) {
            tags.observe(viewLifecycleOwner) { tags ->
                tagAdapter.setList(tags)
            }

            loading.observe(viewLifecycleOwner) {
                if (it) {
                    binding.layoutProgress.visibility = View.VISIBLE
                } else {
                    binding.layoutProgress.visibility = View.INVISIBLE
                }
            }

            problemCountOfTag.observe(viewLifecycleOwner) {
                currentTag?.let { tag ->
                    showLevelSelectionDialog(tag, it)
                }
            }
        }
    }

    override fun onClickTagItem(tagKey: String) {
        currentTag = tagKey
        tagViewModel.hasProblemOfTag(tagKey)
    }

    override fun onClickLevel(level: Int, tag: String) {
        navController.navigate(
            resId = R.id.action_tagFragment_to_problemFragment,
            args = Bundle().putProblemType(TagAndLevelType(tag = tag, level = level))
        )
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
        const val ARGUMENT_PARENT_VIEW_HOME: String = "Home"
    }
}