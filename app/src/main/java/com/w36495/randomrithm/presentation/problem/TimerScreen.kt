package com.w36495.randomrithm.presentation.problem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(
    modifier: Modifier = Modifier,
    leftTime: Int,
    timerState: ProblemTimerState,
    topBarTitle: String,
    onBackPressed: () -> Unit,
    onClickActiveOrPause: () -> Unit,
    onClickComplete: () -> Unit,
    onClickReset: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                windowInsets = TopAppBarDefaults.windowInsets,
                title = { Text(text = topBarTitle) },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Icon(imageVector = Icons.Rounded.Bolt, contentDescription = null)
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val timeFormat = String.format("%02d", leftTime % 60)

                Text(
                    text = "${leftTime / 60} : $timeFormat",
                    fontSize = 58.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(52.dp))

                TimerButtons(
                    modifier = Modifier.fillMaxWidth(),
                    isActive = when (timerState) {
                        ProblemTimerState.RUNNING -> true
                        else -> false
                    },
                    onClickActiveOrPause = onClickActiveOrPause,
                    onClickComplete = onClickComplete,
                    onClickReset = onClickReset
                )
            }

        }
    }
}

@Composable
fun TimerButtons(
    modifier: Modifier = Modifier,
    isActive: Boolean,
    onClickActiveOrPause: () -> Unit,
    onClickComplete: () -> Unit,
    onClickReset: () -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TimerSmallButton(
            buttonIcon = Icons.Rounded.Check,
            onClickButton = onClickComplete
        )
        Spacer(modifier = Modifier.width(16.dp))
        TimerLargeButton(
            isActive = isActive,
            onClickActiveOrPause = onClickActiveOrPause
        )
        Spacer(modifier = Modifier.width(16.dp))
        TimerSmallButton(
            buttonIcon = Icons.Filled.Replay,
            onClickButton = onClickReset
        )
    }
}

@Composable
fun TimerLargeButton(
    modifier: Modifier = Modifier,
    isActive: Boolean = true,
    onClickActiveOrPause: () -> Unit,
) {
    IconButton(
        onClick = { onClickActiveOrPause() },
        modifier = modifier
            .shadow(10.dp, CircleShape)
            .size(98.dp)
            .background(Color.White, CircleShape)
    ) {
        Icon(
            imageVector = if (isActive) Icons.Rounded.Stop else Icons.Rounded.PlayArrow,
            contentDescription = null,
            modifier = Modifier.size(52.dp)
        )
    }
}

@Composable
fun TimerSmallButton(
    modifier: Modifier = Modifier,
    buttonIcon: ImageVector,
    onClickButton: () -> Unit,
) {
    IconButton(
        onClick = { onClickButton() },
        modifier = modifier
            .shadow(10.dp, CircleShape)
            .background(Color.White)
    ) {
        Icon(imageVector = buttonIcon, contentDescription = null)
    }
}