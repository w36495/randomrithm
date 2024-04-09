package com.w36495.randomrithm.presentation.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.w36495.randomrithm.databinding.FragmentLoginBinding
import com.w36495.randomrithm.presentation.home.HomeActivity
import com.w36495.randomrithm.utils.Constants
import com.w36495.randomrithm.utils.showShortToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()
    private val navController by lazy { binding.root.findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeUi()
        setupButtonClickListener()
    }

    private fun subscribeUi() {
        loginViewModel.error.observe(viewLifecycleOwner) {
            binding.tvNotice.text = it
            binding.tvNotice.setTextColor(resources.getColor(com.google.android.material.R.color.design_default_color_error))
        }

        loginViewModel.loginState.observe(viewLifecycleOwner) { hasAccount ->
            if (hasAccount) {
                binding.tvNotice.text = Constants.LOGIN_EXIST_ACCOUNT.message
                binding.tvNotice.setTextColor(resources.getColor(com.google.android.material.R.color.design_default_color_primary))

            } else {
                binding.tvNotice.text = Constants.LOGIN_NOT_EXIST_ACCOUNT.message
                binding.tvNotice.setTextColor(resources.getColor(com.google.android.material.R.color.design_default_color_error))
            }
        }
    }

    private fun setupButtonClickListener() {
        binding.btnCheckId.setOnClickListener {
            val userId = binding.etId.text.toString()
            loginViewModel.checkUserAccount(userId)
        }

        binding.layoutToolbar.setNavigationOnClickListener {
            navController.popBackStack()
        }

        binding.btnLogin.setOnClickListener {
            if (loginViewModel.loginState.value == true) {
                requireContext().showShortToast(Constants.LOGIN_SUCCESS.message)
                loginViewModel.getUserInfo(binding.etId.text.toString())

                moveHomeActivity()
            } else {
                if (binding.etId.text.toString().trim().isNotEmpty()) {
                    requireContext().showShortToast(Constants.LOGIN_SUGGESTION_CHECK_ACCOUNT.message)
                } else {
                    requireContext().showShortToast(Constants.LOGIN_SUGGESTION_INPUT_ACCOUNT.message)
                }
            }
        }
    }

    private fun moveHomeActivity() {
        startActivity(Intent(requireActivity(), HomeActivity::class.java))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG: String = "LoginFragment"
    }
}