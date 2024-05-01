package com.w36495.randomrithm.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.w36495.randomrithm.databinding.DialogLogoutBinding
import com.w36495.randomrithm.utils.Constants
import com.w36495.randomrithm.utils.dialogFragmentResize
import com.w36495.randomrithm.utils.showShortToast

class LogoutDialog : DialogFragment() {
    private var _binding: DialogLogoutBinding? = null
    private val binding: DialogLogoutBinding get() = _binding!!
    private val settingViewModel by activityViewModels<SettingViewModel>()
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DialogLogoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        context?.dialogFragmentResize(this@LogoutDialog, SIZE_WIDTH_PERCENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvCancel.setOnClickListener { dismiss() }
        binding.tvOkay.setOnClickListener {
            settingViewModel.resetUserIdUseCase()
            requireContext().showShortToast(Constants.SUCCESS_LOGOUT.message)
            requireActivity().finish()
            navController.navigate(LogoutDialogDirections.actionLogoutDialogToOnboardingActivity())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val SIZE_WIDTH_PERCENT = 0.9f
        const val TAG: String = "LogoutDialog"
    }
}