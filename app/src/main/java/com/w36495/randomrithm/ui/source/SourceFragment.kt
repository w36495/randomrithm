package com.w36495.randomrithm.ui.source

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.w36495.randomrithm.databinding.FragmentSourceBinding

class SourceFragment : Fragment() {
    private var _binding: FragmentSourceBinding? = null
    private val binding: FragmentSourceBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSourceBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val TAG: String = "SourceFragment"
    }
}