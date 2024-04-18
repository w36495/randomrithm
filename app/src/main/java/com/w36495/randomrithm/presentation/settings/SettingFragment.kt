package com.w36495.randomrithm.presentation.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.w36495.randomrithm.BuildConfig
import com.w36495.randomrithm.databinding.FragmentSettingBinding
import com.w36495.randomrithm.presentation.onboarding.OnboardingActivity
import com.w36495.randomrithm.utils.Constants
import com.w36495.randomrithm.utils.showShortToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding: FragmentSettingBinding get() = _binding!!

    private val viewModel: SettingViewModel by viewModels()
    private val navController by lazy { binding.root.findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sendSurvey()

        if (viewModel.hasUserInfo) {
            binding.dvBetweenSurveyLogout.visibility = View.VISIBLE
            binding.layoutLogout.visibility = View.VISIBLE
        }

        binding.switchTag.setOnCheckedChangeListener { _, isChecked ->
            viewModel.changeTagState(isChecked)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.tagState.collectLatest {
                binding.switchTag.isChecked = it
            }
        }

        binding.layoutLogout.setOnClickListener {
            LogoutDialog( onClickLogout = {
                viewModel.resetUserIdUseCase()
                requireContext().showShortToast(Constants.SUCCESS_LOGOUT.message)
                startActivity(Intent(requireActivity(), OnboardingActivity::class.java))
            }).show(childFragmentManager, LogoutDialog.TAG)
        }
    }

    private fun sendSurvey() {
        binding.layoutSurvey.setOnClickListener {
            val webpage: Uri = Uri.parse(BuildConfig.SURVEY_URL)
            val intent = Intent(Intent.ACTION_VIEW, webpage)

            startActivity(intent)
        }
    }

    companion object {
        const val TAG: String = "SettingFragment"
    }
}