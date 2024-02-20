package com.w36495.randomrithm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.w36495.randomrithm.R
import com.w36495.randomrithm.databinding.ActivityMainBinding
import com.w36495.randomrithm.ui.tag.TagFragment
import com.w36495.randomrithm.ui.level.LevelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { false }

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container_fragment, LevelFragment())
            .addToBackStack(TAG_LEVEL_FRAGMENT)
            .setReorderingAllowed(true)
            .commit()

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { menu ->
            when (menu.itemId) {
                R.id.nav_level -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container_fragment, LevelFragment())
                        .addToBackStack(TAG_LEVEL_FRAGMENT)
                        .setReorderingAllowed(true)
                        .commit()
                    true
                }

                R.id.nav_tag -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container_fragment, TagFragment())
                        .addToBackStack(TagFragment.TAG)
                        .setReorderingAllowed(true)
                        .commit()
                    true
                }

                else -> false
            }
        }
    }

    companion object {
        const val TAG_LEVEL_FRAGMENT: String = "LEVEL_FRAGMENT"
        const val TAG_PROBLEM_FRAGMENT: String = "PROBLEM_FRAGMENT"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}