package com.w36495.randomrithm.ui.tag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.w36495.randomrithm.R
import com.w36495.randomrithm.databinding.FragmentAlgorithmBinding
import com.w36495.randomrithm.ui.problem.ProblemFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TagFragment : Fragment(), TagClickListener {

    private var _binding: FragmentAlgorithmBinding? = null
    private val binding: FragmentAlgorithmBinding get() = _binding!!
    private val tagViewModel: TagViewModel by viewModels()

    private lateinit var tagAdapter: TagAdapter

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
    }

    private fun setupRecyclerView() {
        tagAdapter = TagAdapter()
        binding.containerRecyclerview.apply {
            adapter = tagAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        tagAdapter.setTagClickListener(this)
    }

    override fun onClickTagItem(tagKey: String) {
        parentFragmentManager.beginTransaction()
            .addToBackStack(ProblemFragment.TAG)
            .setReorderingAllowed(true)
            .replace(R.id.container_fragment, ProblemFragment.newInstance(ProblemFragment.INSTANCE_TAG, tagKey))
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG: String = "TagFragment"
    }
}