package com.w36495.randomrithm.ui.source

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.w36495.randomrithm.R
import com.w36495.randomrithm.databinding.FragmentSourceBinding
import com.w36495.randomrithm.ui.problem.ProblemFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        initButton()
    }

    private fun initButton() {
        binding.cardCamp.setOnClickListener {
            moveProblem(SOURCE_NAME_CAMP)
        }
        binding.cardIcpc.setOnClickListener {
            moveProblem(SOURCE_NAME_ICPC)
        }
        binding.cardOlympiad.setOnClickListener {
            moveProblem(SOURCE_NAME_OLYMPIAD)
        }
        binding.cardUniversity.setOnClickListener {
            moveProblem(SOURCE_NAME_UNIVERSITY)
        }
        binding.cardHighSchool.setOnClickListener {
            moveProblem(SOURCE_NAME_HIGH_SCHOOL)
        }
    }

    private fun moveProblem(source: String) {
        parentFragmentManager.beginTransaction()
            .addToBackStack(ProblemFragment.TAG)
            .setReorderingAllowed(true)
            .replace(
                R.id.container_fragment,
                ProblemFragment.newInstance(ProblemFragment.INSTANCE_SOURCE, source)
            )
            .commit()
    }

    private fun initButtonDesc() {
        val desc = resources.getString(R.string.source_desc)

        binding.tvCampDesc.text = resources.getString(R.string.source_btn_camp).plus(desc)
        binding.tvIcpcDesc.text = resources.getString(R.string.source_btn_icpc).plus(desc)
        binding.tvHighSchoolDesc.text = resources.getString(R.string.source_btn_high_school).plus(desc)
        binding.tvOlympiadDesc.text = resources.getString(R.string.source_btn_olympiad).plus(desc)
        binding.tvUniversityDesc.text =resources.getString(R.string.source_btn_university).plus(desc)
    }

    companion object {
        private const val SOURCE_NAME_CAMP: String = "camp"
        private const val SOURCE_NAME_OLYMPIAD: String = "olympiad"
        private const val SOURCE_NAME_ICPC: String = "icpc"
        private const val SOURCE_NAME_HIGH_SCHOOL: String = "hs"
        private const val SOURCE_NAME_UNIVERSITY: String = "univ"

        const val TAG: String = "SourceFragment"
    }
}