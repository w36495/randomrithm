package com.w36495.randomrithm.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.w36495.randomrithm.BuildConfig
import com.w36495.randomrithm.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding: FragmentSettingBinding get() = _binding!!

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