package com.w36495.randomrithm.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.w36495.randomrithm.R
import com.w36495.randomrithm.databinding.FragmentOnboardingBinding
import com.w36495.randomrithm.presentation.MainActivity
import com.w36495.randomrithm.presentation.home.HomeActivity
import com.w36495.randomrithm.presentation.login.LoginViewModel
import com.w36495.randomrithm.utils.showShortToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnboardingFragment : Fragment() {
    private var _binding: FragmentOnboardingBinding? = null
    private val binding: FragmentOnboardingBinding get() = _binding!!
    private val loginViewModel by viewModels<LoginViewModel>()
    private val navController by lazy { binding.root.findNavController() }

    private var hasUserId = false
    private lateinit var userId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            loginViewModel.loadUserId.collectLatest {
                it?.let {
                    userId = it
                    hasUserId = true
                }
            }
        }

        loginViewModel.cacheState.observe(viewLifecycleOwner) {
            if (it) {
                requireContext().showShortToast("$userId 계정으로 로그인되었습니다.")
                navController.navigate(R.id.nav_home)
            }
        }

        binding.btnLogin.setOnClickListener {
            if (hasUserId) loginViewModel.setCacheUserId(userId)
            else navController.navigate(R.id.action_onboardingFragment_to_loginFragment)
        }

        binding.btnJustLooking.setOnClickListener {
            navController.navigate(R.id.nav_main)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}