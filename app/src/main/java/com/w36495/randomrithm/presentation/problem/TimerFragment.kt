package com.w36495.randomrithm.presentation.problem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.w36495.randomrithm.presentation.problem.ui.theme.RandomrithmTheme

class TimerFragment : Fragment() {
    private val timerViewModel by viewModels<ProblemTimerViewModel>()
    private val navController by lazy { findNavController() }
    private val args by navArgs<TimerFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                RandomrithmTheme {
                    TimerScreen(
                        topBarTitle = "${args.problemId}번",
                        timerState = timerViewModel.timerState.value,
                        passedTime = timerViewModel.passedTime.value,
                        onBackPressed = { navController.popBackStack() },
                        onClickActiveOrPause = {
                            when (timerViewModel.timerState.value) {
                                ProblemTimerState.STOP -> timerViewModel.startProblemTimer()
                                else -> timerViewModel.pauseProblemTimer()
                            }
                        },
                        onClickComplete = {
                            timerViewModel.pauseProblemTimer()
                            navController.navigate(TimerFragmentDirections.actionTimerFragmentToTimerCompleteDialog(timerViewModel.passedTime.value))
                        },
                        onClickReset = {
                            timerViewModel.resetProblemTimer()
                        }
                    )
                }
            }
        }
    }
}