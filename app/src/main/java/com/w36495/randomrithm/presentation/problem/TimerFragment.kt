package com.w36495.randomrithm.presentation.problem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.w36495.randomrithm.presentation.problem.ui.theme.RandomrithmTheme

class TimerFragment : Fragment() {
    private val timerViewModel by viewModels<ProblemTimerViewModel>()
    private val navController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                RandomrithmTheme {
                    TimerScreen(
                        topBarTitle = "1234번",
                        timerState = timerViewModel.timerState.value,
                        leftTime = timerViewModel.solveTime.value,
                        onBackPressed = { navController.popBackStack() },
                        onClickActiveOrPause = {
                            when (timerViewModel.timerState.value) {
                                ProblemTimerState.STOP -> timerViewModel.startProblemTimer()
                                else -> timerViewModel.pauseProblemTimer()
                            }
                        },
                        onClickComplete = {
                            // TODO : 완료 다이얼로그 표시
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