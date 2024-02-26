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

        selectLevel()
    }

    fun setLevelSelectionClickListener(levelSelectionClickListener: LevelSelectionClickListener) {
        this.levelSelectionClickListener = levelSelectionClickListener
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