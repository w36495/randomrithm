package com.w36495.randomrithm.presentation.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.w36495.randomrithm.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container_fragment_home, HomeFragment())
            .commit()
    }
}