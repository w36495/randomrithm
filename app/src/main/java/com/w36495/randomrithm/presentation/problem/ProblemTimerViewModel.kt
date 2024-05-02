package com.w36495.randomrithm.presentation.problem

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProblemTimerViewModel : ViewModel() {
    private var job: Job? = null
    val solveTime = mutableIntStateOf(2400)

    private var _timerState = mutableStateOf(ProblemTimerState.STOP)
    val timerState: MutableState<ProblemTimerState> get() = _timerState

    fun startProblemTimer() {
        _timerState.value = ProblemTimerState.RUNNING

        if (solveTime.intValue <= 0) return
        job = decreaseSecond().onEach {
            solveTime.intValue = it
        }.launchIn(viewModelScope)
    }

    fun pauseProblemTimer() {
        job?.cancel()
        _timerState.value = ProblemTimerState.STOP
    }

    fun resetProblemTimer() {
        job?.cancel()
        _timerState.value = ProblemTimerState.STOP
        solveTime.intValue = 2400
    }

    private fun decreaseSecond(): Flow<Int> = flow {
        var i = solveTime.intValue
        while (i > 0) {
            delay(1000)
            emit(--i)
        }
    }
}

enum class ProblemTimerState {
    RUNNING, STOP
}