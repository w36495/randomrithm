package com.w36495.randomrithm.ui.source

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.w36495.randomrithm.R
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButtonDesc()
    }

    private fun initButtonDesc() {
        val desc = resources.getString(R.string.source_desc)

        binding.tvCampDesc.text = resources.getString(R.string.source_btn_camp).plus(desc)
        binding.tvContestDesc.text = resources.getString(R.string.source_btn_contest).plus(desc)
        binding.tvIcpcDesc.text = resources.getString(R.string.source_btn_icpc).plus(desc)
        binding.tvHighSchoolDesc.text = resources.getString(R.string.source_btn_high_school).plus(desc)
        binding.tvOlympiadDesc.text = resources.getString(R.string.source_btn_olympiad).plus(desc)
        binding.tvUniversityDesc.text =resources.getString(R.string.source_btn_university).plus(desc)
    }

    companion object {
        const val TAG: String = "SourceFragment"
    }
}