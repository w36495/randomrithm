package com.w36495.randomrithm.presentation.tag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.w36495.randomrithm.R
import com.w36495.randomrithm.databinding.DialogLevelSelectionBinding
import com.w36495.randomrithm.domain.entity.TagAndLevelType
import com.w36495.randomrithm.domain.entity.TagType
import com.w36495.randomrithm.utils.putProblemType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LevelSelectionDialog : BottomSheetDialogFragment() {
    private var _binding: DialogLevelSelectionBinding? = null
    private val binding: DialogLevelSelectionBinding get() = _binding!!

    private val tagViewModel by viewModels<TagViewModel>()
    private val navController by lazy { requireActivity().findNavController(R.id.container_fragment) }
    private val args: LevelSelectionDialogArgs by navArgs<LevelSelectionDialogArgs>()

    private var tag: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogLevelSelectionBinding.inflate(inflater, container, false)
        tagViewModel.hasProblemOfTag(args.tag)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tagViewModel.problemCountOfTag.observe(viewLifecycleOwner) {
            initButtons(it.toBooleanArray())
        }

        selectLevel()
    }

    private fun initButtons(levels: BooleanArray) {
        if (!levels[0]) {
            binding.tvBronze.isEnabled = false
        }
        if (!levels[1]) {
            binding.tvSilver.isEnabled = false
        }
        if (!levels[2]) {
            binding.tvGold.isEnabled = false
        }
        if (!levels[3]) {
            binding.tvPlatinum.isEnabled = false
        }
        if (!levels[4]) {
            binding.tvDiamond.isEnabled = false
        }
        if (!levels[5]) {
            binding.tvRuby.isEnabled = false
        }
    }

    private fun selectLevel() {
        binding.tvBronze.setOnClickListener { selectLevel(0) }
        binding.tvSilver.setOnClickListener { selectLevel(1) }
        binding.tvGold.setOnClickListener { selectLevel(2) }
        binding.tvPlatinum.setOnClickListener { selectLevel(3) }
        binding.tvDiamond.setOnClickListener { selectLevel(4) }
        binding.tvRuby.setOnClickListener { selectLevel(5) }
        binding.tvAll.setOnClickListener { selectLevel(-1) }
    }

    private fun selectLevel(level: Int) {
        val bundle = Bundle()
        when (level) {
            -1 -> bundle.putProblemType(TagType(tag = args.tag))
            else -> bundle.putProblemType(TagAndLevelType(tag = args.tag, level = level))
        }

        navController.navigate(
            R.id.action_levelSelectionDialog_to_problemFragment,
            bundle
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        tag = null
    }

    companion object {
        const val TAG: String = "LevelSelectionDialog"
    }
}