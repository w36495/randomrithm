package com.w36495.randomrithm.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.w36495.randomrithm.R
import com.w36495.randomrithm.databinding.FragmentHomeBinding
import com.w36495.randomrithm.domain.entity.EssentialType
import com.w36495.randomrithm.domain.entity.LevelType
import com.w36495.randomrithm.domain.entity.ProblemType
import com.w36495.randomrithm.domain.entity.SolvedCountType
import com.w36495.randomrithm.domain.entity.SourceType
import com.w36495.randomrithm.domain.entity.SproutType
import com.w36495.randomrithm.domain.entity.Tag
import com.w36495.randomrithm.domain.entity.TagType
import com.w36495.randomrithm.domain.entity.User
import com.w36495.randomrithm.domain.usecase.USER_ID
import com.w36495.randomrithm.domain.usecase.dataStore
import com.w36495.randomrithm.presentation.problem.ProblemFragment
import com.w36495.randomrithm.utils.putProblemType
import com.w36495.randomrithm.utils.showShortToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), PopularAlgorithmClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!
    private val homeViewModel by viewModels<HomeViewModel>()
    private val popularAlgorithmAdapter by lazy {
        PopularAlgorithmAdapter().apply {
            setPopularAlgorithmClickListener(this@HomeFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupPopularAlgorithm() {
        binding.rvPopularAlgorithm.apply {
            adapter = popularAlgorithmAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(SpaceItemDecoration(12))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeUi()
        setupPopularAlgorithm()
        setupButtons()

        viewLifecycleOwner.lifecycleScope.launch {
            requireContext().dataStore.data.map {
                it[USER_ID]
            }.collectLatest { userId ->
                homeViewModel.getUserProfile(userId.toString())
            }
        }
    }

    private fun subscribeUi() {
        with(homeViewModel) {
            user.observe(viewLifecycleOwner) { user ->
                setupUserProfile(user)
            }

            error.observe(viewLifecycleOwner) { message ->
                requireContext().showShortToast(message)
            }

            tags.observe(viewLifecycleOwner) { tags ->
                popularAlgorithmAdapter.setupPopularTags(tags)
            }
        }
    }

    private fun setupUserProfile(user: User) {
        val colors = resources.getIntArray(R.array.levelColorList)

        binding.layoutAppbar.setBackgroundColor(colors[user.tier])
        binding.tvId.text = user.id
        binding.tvSolvedProblemCount.text = user.solvedCount.toString()
    }

    private fun setupButtons() {
        with (binding) {
            btnRecommendFirst.setOnClickListener { moveProblemFragment(SproutType) }
            btnRecommendSecond.setOnClickListener { moveProblemFragment(SolvedCountType(min = 10_000)) }
            btnRecommendThird.setOnClickListener { moveProblemFragment(EssentialType(min = 2, max = 5)) }

            btnBronze.setOnClickListener { moveProblemFragment(LevelType(level = 'b')) }
            btnSilver.setOnClickListener { moveProblemFragment(LevelType(level = 's')) }
            btnGold.setOnClickListener { moveProblemFragment(LevelType(level = 'g')) }
            btnPlatinum.setOnClickListener { moveProblemFragment(LevelType(level = 'p')) }
            btnDiamond.setOnClickListener { moveProblemFragment(LevelType(level = 'd')) }
            btnRuby.setOnClickListener { moveProblemFragment(LevelType(level = 'r')) }

            btnIcpc.setOnClickListener { moveProblemFragment(SourceType(source = "icpc")) }
            btnOlympiad.setOnClickListener { moveProblemFragment(SourceType(source = "olympiad")) }
            btnUniversity.setOnClickListener { moveProblemFragment(SourceType(source = "univ")) }
        }
    }

    private fun moveProblemFragment(problemType: ProblemType) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container_fragment_home, ProblemFragment().apply {
                arguments = Bundle().putProblemType(problemType)
            })
            .addToBackStack(ProblemFragment.TAG)
            .setReorderingAllowed(true)
            .commit()
    }

    override fun onClickPopularAlgorithm(tag: Tag) {
        moveProblemFragment(TagType(tag = tag.key))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG: String = "HomeFragment"
    }
}