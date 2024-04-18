package com.w36495.randomrithm.presentation.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.w36495.randomrithm.R
import com.w36495.randomrithm.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private var _binding: ActivityHomeBinding? = null
    private val binding: ActivityHomeBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        val navHost = supportFragmentManager.findFragmentById(R.id.container_fragment_home) as NavHostFragment
        binding.bottomNavigationHome.setupWithNavController(navHost.navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}