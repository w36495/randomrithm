package com.w36495.randomrithm.ui.algorithm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.w36495.randomrithm.R
import com.w36495.randomrithm.data.entity.Algorithm
import com.w36495.randomrithm.data.entity.AlgorithmItem
import com.w36495.randomrithm.data.entity.Problem
import com.w36495.randomrithm.data.entity.ProblemItem
import com.w36495.randomrithm.data.remote.RetrofitClient
import com.w36495.randomrithm.databinding.FragmentAlgorithmBinding
import com.w36495.randomrithm.ui.AlgorithmItemClickListener
import com.w36495.randomrithm.ui.MainActivity
import com.w36495.randomrithm.ui.ProblemClickListener
import com.w36495.randomrithm.ui.problem.ProblemFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlgorithmFragment : Fragment(), AlgorithmItemClickListener, ProblemClickListener {

    private var _binding: FragmentAlgorithmBinding? = null
    private val binding: FragmentAlgorithmBinding get() = _binding!!

    private lateinit var algorithmAdapter: AlgorithmAdapter

    private var currentTag: String? = null

    private val categoryList = MutableLiveData<List<AlgorithmItem>>()
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
        getCategoryList()

        categoryList.observe(requireActivity()) {
            algorithmAdapter.setList(it)
        }

        problemList.observe(requireActivity()) {
            val randomProblemId = it.random().problemId
            val bundle = Bundle().apply {
                currentTag?.let {
                    this.putString("tag", it)
                }
                this.putInt("problemId", randomProblemId)
            }

            val problemFragment = ProblemFragment().apply {
                setProblemCLickListener(this@AlgorithmFragment)
                arguments = bundle
            }

            parentFragmentManager.beginTransaction()
                .replace(R.id.container_fragment, problemFragment)
                .addToBackStack(MainActivity.TAG_PROBLEM_FRAGMENT)
                .setReorderingAllowed(true)
                .commit()
        }
    }

    private fun setupRecyclerView() {
        algorithmAdapter = AlgorithmAdapter()
        binding.containerRecyclerview.apply {
            adapter = algorithmAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        algorithmAdapter.setAlgorithmItemClickListener(this)
    }

    private fun getCategoryList() {
        RetrofitClient.algorithmAPI.getCountOfAlgorithm().enqueue(object : Callback<Algorithm> {
            override fun onResponse(call: Call<Algorithm>, response: Response<Algorithm>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        categoryList.value = it.items
                    }
                } else {
                    Log.d("Not-Success(getCategoryList)", response.code().toString())
                }
            }

            override fun onFailure(call: Call<Algorithm>, t: Throwable) {
                Log.d("Failed(getCategoryList)", t.localizedMessage)
            }
        })
    }

    private fun getProblemList(tag: String) {
        val requestQuery = "solvable:true+tag:${tag}"

        RetrofitClient.algorithmAPI.getProblemList(requestQuery).enqueue(object : Callback<Problem> {
            override fun onResponse(call: Call<Problem>, response: Response<Problem>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        problemList.value = it.items
                    }
                } else {
                    Log.d("Not-Success(getProblemList)", response.code().toString())
                }
            }
            override fun onFailure(call: Call<Problem>, t: Throwable) {
                Log.d("Failed(getProblemList)", t.localizedMessage)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickAlgorithmItem(tag: String) {
        currentTag = tag
        getProblemList(tag)
    }

    override fun onClickNextProblem(tag: String) {
        getProblemList(tag)
    }
}