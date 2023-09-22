package com.w36495.randomrithm.ui.algorithm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.w36495.randomrithm.data.entity.Algorithm
import com.w36495.randomrithm.data.entity.AlgorithmItem
import com.w36495.randomrithm.data.remote.RetrofitClient
import com.w36495.randomrithm.databinding.FragmentAlgorithmBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlgorithmFragment : Fragment() {

    private var _binding: FragmentAlgorithmBinding? = null
    private val binding: FragmentAlgorithmBinding get() = _binding!!

    private lateinit var algorithmAdapter: AlgorithmAdapter

    private val categoryList = MutableLiveData<List<AlgorithmItem>>()

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
        getCategoryOfAlgorithm()
        categoryList.observe(requireActivity()) {
            algorithmAdapter.setList(it)
        }
    }

    private fun setupRecyclerView() {
        algorithmAdapter = AlgorithmAdapter()
        binding.containerRecyclerview.apply {
            adapter = algorithmAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun getCategoryOfAlgorithm() {
        RetrofitClient.algorithmAPI.getCountOfAlgorithm().enqueue(object: Callback<Algorithm> {
            override fun onResponse(call: Call<Algorithm>, response: Response<Algorithm>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        categoryList.value = it.items
                    }
                }
            }

            override fun onFailure(call: Call<Algorithm>, t: Throwable) {
                Log.d("ALGORITHM_LIST(getCategoryOfAlgorithm)", t.localizedMessage)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}