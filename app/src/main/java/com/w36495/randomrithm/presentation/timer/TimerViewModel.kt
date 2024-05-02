package com.w36495.randomrithm.presentation.timer

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

    private var _timerState = mutableStateOf(ProblemTimerState.STOP)
    val timerState: MutableState<ProblemTimerState> get() = _timerState
    val passedTime = mutableIntStateOf(INIT_TIME)

    fun startProblemTimer() {
        _timerState.value = ProblemTimerState.RUNNING

        if (passedTime.intValue >= MAX_TIME) return
        job = increaseSecond().onEach {
            passedTime.intValue = it
        }.launchIn(viewModelScope)
    }

    fun pauseProblemTimer() {
        job?.cancel()
        _timerState.value = ProblemTimerState.STOP
    }

    fun resetProblemTimer() {
        job?.cancel()
        _timerState.value = ProblemTimerState.STOP
        passedTime.intValue = INIT_TIME
    }


    private fun increaseSecond(): Flow<Int> = flow {
        var time = passedTime.intValue

        while (time < MAX_TIME) {
            delay(1_000)
            emit(++time)
        }
    }

    companion object {
        private const val MAX_TIME: Int = 86_320
        private const val INIT_TIME: Int = 0
    }
}

enum class ProblemTimerState {
    RUNNING, STOP
}