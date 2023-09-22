package com.w36495.randomrithm.ui.level

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.w36495.randomrithm.R
import com.w36495.randomrithm.data.entity.Level
import com.w36495.randomrithm.data.remote.RetrofitClient
import com.w36495.randomrithm.databinding.FragmentLevelListBinding
import com.w36495.randomrithm.ui.MainActivity
import com.w36495.randomrithm.ui.problem.ProblemFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LevelListFragment(
    private val level: Int
) : Fragment() {

    private var _binding: FragmentLevelListBinding? = null
    private val binding: FragmentLevelListBinding get() = _binding!!

    private lateinit var levelListAdapter: LevelListAdapter

    private val _levelList = MutableLiveData<List<Level>>()
    private val levelList = arrayListOf<Level>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLevelListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListView()
        setupListViewClickEvent()
        getCountOfLevel()

        _levelList.observe(requireActivity()) {
            when (level) {
                0 -> levelList.add(it[0])
                1 -> {
                    for (i in 1..5) {
                        levelList.add(it[i])
                    }
                }

                2 -> {
                    for (i in 6..10) {
                        levelList.add(it[i])
                    }
                }

                3 -> {
                    for (i in 11..15) {
                        levelList.add(it[i])
                    }
                }

                4 -> {
                    for (i in 16..20) {
                        levelList.add(it[i])
                    }
                }

                5 -> {
                    for (i in 21..25) {
                        levelList.add(it[i])
                    }
                }

                else -> {
                    for (i in 26..30) {
                        levelList.add(it[i])
                    }
                }
            }
            levelListAdapter.setLevelList(levelList)
        }
    }

    private fun setupListView() {
        levelListAdapter = LevelListAdapter()
        levelListAdapter.setLevelList(levelList)
        binding.containerListview.adapter = levelListAdapter
    }

    private fun setupListViewClickEvent() {
        binding.containerListview.setOnItemClickListener { adapterView, view, position, id ->
            parentFragmentManager.beginTransaction()
                .replace(R.id.container_fragment, ProblemFragment())
                .addToBackStack(MainActivity.TAG_PROBLEM_FRAGMENT)
                .setReorderingAllowed(true)
                .commit()
        }
    }

    private fun getCountOfLevel() {
        RetrofitClient.levelApi.getCountByLevel().enqueue(object: Callback<List<Level>> {
            override fun onResponse(
                call: Call<List<Level>>,
                response: Response<List<Level>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _levelList.value = it
                    }
                }
            }
            override fun onFailure(call: Call<List<Level>>, t: Throwable) {
                Log.d("LEVEL_LIST(getCountOfLevel)", t.localizedMessage)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}