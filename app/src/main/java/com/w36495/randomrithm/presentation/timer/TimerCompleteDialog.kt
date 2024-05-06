package com.w36495.randomrithm.presentation.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.w36495.randomrithm.presentation.timer.ui.theme.RandomrithmTheme

class TimerCompleteDialog : DialogFragment() {
    private val navController by lazy { findNavController() }
    private val args by navArgs<TimerCompleteDialogArgs>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                RandomrithmTheme {
                    TimerCompleteDialogScreen(
                        completedTime = args.passedTime,
                        onClickProblemScreen = {
                            navController.navigate(TimerCompleteDialogDirections.actionTimerCompleteDialogToProblemFragment())
                        },
                        onClickHomeScreen = {
                            navController.navigate(TimerCompleteDialogDirections.actionTimerCompleteDialogToHomeFragment())
                        }
                    )
                }
            }
        }
    }
}