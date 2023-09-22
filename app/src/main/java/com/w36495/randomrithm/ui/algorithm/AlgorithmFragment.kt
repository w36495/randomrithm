package com.w36495.randomrithm.ui.algorithm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.w36495.randomrithm.databinding.FragmentAlgorithmBinding

class AlgorithmFragment : Fragment() {

    private var _binding: FragmentAlgorithmBinding? = null
    private val binding: FragmentAlgorithmBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlgorithmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}