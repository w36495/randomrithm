package com.w36495.randomrithm.presentation.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.w36495.randomrithm.databinding.ActivityOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {
    private var _binding: ActivityOnboardingBinding? = null
    private val binding: ActivityOnboardingBinding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { false }

        _binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}