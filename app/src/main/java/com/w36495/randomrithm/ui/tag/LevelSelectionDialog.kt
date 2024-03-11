package com.w36495.randomrithm.ui.tag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.w36495.randomrithm.databinding.DialogLevelSelectionBinding

class LevelSelectionDialog : BottomSheetDialogFragment() {
    private var _binding: DialogLevelSelectionBinding? = null
    private val binding: DialogLevelSelectionBinding get() = _binding!!

    private lateinit var levelSelectionClickListener: LevelSelectionClickListener

    private var tag: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogLevelSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tag = arguments?.takeIf { it.containsKey("tag") }?.getString("tag")
        val levels = arguments?.takeIf { it.containsKey("levels") }?.getBooleanArray("levels")

        if (levels != null) {
            initButtons(levels)
        }
        selectLevel()
    }

    fun setLevelSelectionClickListener(levelSelectionClickListener: LevelSelectionClickListener) {
        this.levelSelectionClickListener = levelSelectionClickListener
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
        tag?.let {
            levelSelectionClickListener.onClickLevel(level, it)
        }
        dismiss()
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