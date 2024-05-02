package com.w36495.randomrithm.presentation.problem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.w36495.randomrithm.utils.TimeFormat

@Composable
fun TimerCompleteDialogScreen(
    completedTime: Int,
    onClickProblemScreen: () -> Unit,
    onClickHomeScreen: () -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "🎉", fontSize = 52.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                BoldText(
                    modifier = Modifier.padding(end = 4.dp),
                    text = TimeFormat.formattedTimeToDisplayKr(completedTime)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "몰입하여 문제를 해결하셨습니다.",
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { onClickProblemScreen() }) {
                Text(text = "문제 화면으로 이동")
            }
            OutlinedButton(onClick = { onClickHomeScreen() }) {
                Text(text = "홈 화면으로 이동")
            }
        }
    }
}

@Composable
private fun BoldText(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier,
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    )
}