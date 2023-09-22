package com.w36495.randomrithm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.w36495.randomrithm.R
import com.w36495.randomrithm.databinding.ActivityMainBinding
import com.w36495.randomrithm.ui.algorithm.AlgorithmFragment
import com.w36495.randomrithm.ui.level.LevelFragment

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

                R.id.nav_algorithm -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container_fragment, AlgorithmFragment())
                        .addToBackStack(TAG_ALGORITHM_FRAGMENT)
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
        const val TAG_ALGORITHM_FRAGMENT: String = "ALGORITHM_FRAGMENT"
        const val TAG_PROBLEM_FRAGMENT: String = "PROBLEM_FRAGMENT"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}