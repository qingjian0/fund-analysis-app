package com.example.androidmvvmclean.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BayesBar(
    upProbability: Double, // 上涨概率
    neutralProbability: Double, // 持平概率
    downProbability: Double, // 下跌概率
    height: Dp = 40.dp
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // 上涨概率
            Box(
                modifier = Modifier
                    .weight(upProbability)
                    .height(height),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF4CAF50)) // 绿色
                ) {}
                Text(
                    text = "上涨 ${upProbability.toInt()}%",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            
            // 持平概率
            Box(
                modifier = Modifier
                    .weight(neutralProbability)
                    .height(height),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFFC107)) // 黄色
                ) {}
                Text(
                    text = "持平 ${neutralProbability.toInt()}%",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            
            // 下跌概率
            Box(
                modifier = Modifier
                    .weight(downProbability)
                    .height(height),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF44336)) // 红色
                ) {}
                Text(
                    text = "下跌 ${downProbability.toInt()}%",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
