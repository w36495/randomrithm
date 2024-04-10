package com.w36495.randomrithm.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.w36495.randomrithm.R
import com.w36495.randomrithm.databinding.FragmentHomeBinding
import com.w36495.randomrithm.domain.entity.User
import com.w36495.randomrithm.utils.showShortToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeUi()
    }

    private fun subscribeUi() {
        with(homeViewModel) {
            user.observe(viewLifecycleOwner) { user ->
                homeViewModel.fetchSolvedProblems(user.id, user.solvedCount)
                setupUserProfile(user)
            }

            error.observe(viewLifecycleOwner) { message ->
                requireContext().showShortToast(message)
            }
        }
    }

    private fun setupUserProfile(user: User) {
        val colors = resources.getIntArray(R.array.levelColorList)

        binding.layoutAppbar.setBackgroundColor(colors[user.tier])
        binding.tvId.text = user.id
        binding.tvSolvedProblemCount.text = user.solvedCount.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG: String = "HomeFragment"
    }
}