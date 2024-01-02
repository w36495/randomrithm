package com.w36495.randomrithm.ui.algorithm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.w36495.randomrithm.data.datasource.ProblemRemoteDataSource
import com.w36495.randomrithm.data.datasource.TagRemoteDataSource
import com.w36495.randomrithm.data.entity.ProblemDTO
import com.w36495.randomrithm.data.entity.ProblemItem
import com.w36495.randomrithm.data.remote.RetrofitClient
import com.w36495.randomrithm.databinding.FragmentAlgorithmBinding
import com.w36495.randomrithm.domain.repository.ProblemRepositoryImpl
import com.w36495.randomrithm.domain.repository.TagRepositoryImpl
import com.w36495.randomrithm.domain.usecase.GetProblemsByTagUseCase
import com.w36495.randomrithm.domain.usecase.GetTagsUseCase
import com.w36495.randomrithm.ui.TagClickListener
import com.w36495.randomrithm.ui.ProblemOfAlgorithmClickListener
import com.w36495.randomrithm.ui.viewmodel.TagViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TagFragment : Fragment(), TagClickListener, ProblemOfAlgorithmClickListener {

    private var _binding: FragmentAlgorithmBinding? = null
    private val binding: FragmentAlgorithmBinding get() = _binding!!
    private lateinit var tagViewModel: TagViewModel
    private lateinit var tagViewModelFactory: TagViewModelFactory

    private lateinit var tagAdapter: TagAdapter

    private var currentTag: String? = null

    private val problemList = MutableLiveData<List<ProblemItem>>()

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
        getCategoryList()

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

    private fun getCategoryList() {
//        RetrofitClient.algorithmAPI.getCountOfAlgorithm().enqueue(object : Callback<AlgorithmDTO> {
//            override fun onResponse(call: Call<AlgorithmDTO>, response: Response<AlgorithmDTO>) {
//                if (response.isSuccessful) {
//                    response.body()?.let {
//                        categoryList.value = it.items
//                    }
//                } else {
//                    Log.d("Not-Success(getCategoryList)", response.code().toString())
//                }
//            }
//
//            override fun onFailure(call: Call<AlgorithmDTO>, t: Throwable) {
//                Log.d("Failed(getCategoryList)", t.localizedMessage)
//            }
//        })
    }

    private fun getProblemList(tag: String) {
        val requestQuery = "solvable:true+tag:${tag}"

        RetrofitClient.tagAPI.getProblemList(requestQuery).enqueue(object : Callback<ProblemDTO> {
            override fun onResponse(call: Call<ProblemDTO>, response: Response<ProblemDTO>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        problemList.value = it.items
                    }
                } else {
                    Log.d("Not-Success(getProblemList)", response.code().toString())
                }
            }
            override fun onFailure(call: Call<ProblemDTO>, t: Throwable) {
                Log.d("Failed(getProblemList)", t.localizedMessage)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickTagItem(tagKey: String) {
        tagViewModel.getProblemsByTag(tagKey)
    }

    override fun onClickNextProblem(tag: String) {
        getProblemList(tag)
    }

    companion object {
        const val TAG: String = "TagFragment"
    }
}