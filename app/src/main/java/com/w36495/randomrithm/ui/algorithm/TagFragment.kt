package com.w36495.randomrithm.ui.algorithm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.w36495.randomrithm.data.datasource.ProblemRemoteDataSource
import com.w36495.randomrithm.data.datasource.TagRemoteDataSource
import com.w36495.randomrithm.data.remote.RetrofitClient
import com.w36495.randomrithm.databinding.FragmentAlgorithmBinding
import com.w36495.randomrithm.domain.repository.ProblemRepositoryImpl
import com.w36495.randomrithm.domain.repository.TagRepositoryImpl
import com.w36495.randomrithm.domain.usecase.GetProblemsByTagUseCase
import com.w36495.randomrithm.domain.usecase.GetTagsUseCase
import com.w36495.randomrithm.ui.TagClickListener
import com.w36495.randomrithm.ui.ProblemOfAlgorithmClickListener
import com.w36495.randomrithm.ui.viewmodel.TagViewModelFactory

class TagFragment : Fragment(), TagClickListener, ProblemOfAlgorithmClickListener {

    private var _binding: FragmentAlgorithmBinding? = null
    private val binding: FragmentAlgorithmBinding get() = _binding!!
    private lateinit var tagViewModel: TagViewModel
    private lateinit var tagViewModelFactory: TagViewModelFactory

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
        setupViewModel()

        tagViewModel.getTags()
        tagViewModel.tags.observe(requireActivity()) {
            tagAdapter.setList(it)
        }

        tagViewModel.problems.observe(requireActivity()) {
            Log.d(TAG, it.toString())
        }
//        problemList.observe(requireActivity()) {
//            val randomProblemId = it.random().problemId
//            val bundle = Bundle().apply {
//                currentTag?.let {
//                    this.putString("tag", it)
//                }
//                this.putInt("problemId", randomProblemId)
//            }
//
//            val problemFragment = ProblemFragment().apply {
//                setProblemOfAlgorithmCLickListener(this@AlgorithmFragment)
//                arguments = bundle
//            }
//
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.container_fragment, problemFragment)
//                .addToBackStack(MainActivity.TAG_PROBLEM_FRAGMENT)
//                .setReorderingAllowed(true)
//                .commit()
//        }
    }

    private fun setupRecyclerView() {
        tagAdapter = TagAdapter()
        binding.containerRecyclerview.apply {
            adapter = tagAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        tagAdapter.setTagClickListener(this)
    }

    private fun setupViewModel() {
        tagViewModelFactory = TagViewModelFactory(
            GetTagsUseCase(TagRepositoryImpl(TagRemoteDataSource(RetrofitClient.tagAPI))),
            GetProblemsByTagUseCase(ProblemRepositoryImpl(ProblemRemoteDataSource(RetrofitClient.problemAPI)))
            )
        tagViewModel = ViewModelProvider(requireActivity(), tagViewModelFactory)[TagViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickTagItem(tagKey: String) {
        tagViewModel.getProblemsByTag(tagKey)
    }

    override fun onClickNextProblem(tag: String) {

    }

    companion object {
        const val TAG: String = "TagFragment"
    }
}