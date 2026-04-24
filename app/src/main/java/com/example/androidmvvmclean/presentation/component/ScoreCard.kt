package com.example.androidmvvmclean.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ScoreCard(
    score: Double,
    dimensionScores: Map<String, Double>,
    size: Dp = 180.dp
) {
    Box(
        modifier = Modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val radius = size.toPx() / 2
            val strokeWidth = 12.dp.toPx()
            val centerX = size.toPx() / 2
            val centerY = size.toPx() / 2
            
            // 绘制背景圆环
            drawCircle(
                color = MaterialTheme.colorScheme.surfaceVariant,
                radius = radius - strokeWidth / 2,
                style = Stroke(width = strokeWidth)
            )
            
            // 绘制主进度
            val progressAngle = (score / 100) * 360
            drawArc(
                color = MaterialTheme.colorScheme.primary,
                startAngle = -90f,
                sweepAngle = progressAngle.toFloat(),
                useCenter = false,
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round
                )
            )
            
            // 绘制维度小分
            val dimensionCount = dimensionScores.size
            val angleStep = 360f / dimensionCount
            var currentAngle = -90f
            
            dimensionScores.forEach { (name, value) ->
                val dimensionRadius = radius - 24.dp.toPx()
                val endRadius = dimensionRadius * (value / 100)
                val startX = centerX
                val startY = centerY
                val endX = centerX + endRadius * cos(currentAngle * PI / 180)
                val endY = centerY + endRadius * sin(currentAngle * PI / 180)
                
                drawLine(
                    color = MaterialTheme.colorScheme.secondary,
                    start = androidx.compose.ui.geometry.Offset(startX, startY),
                    end = androidx.compose.ui.geometry.Offset(endX.toFloat(), endY.toFloat()),
                    strokeWidth = 4.dp.toPx()
                )
                
                currentAngle += angleStep
            }
        }
        
        // 显示分数
        Text(
            text = "${score.toInt()}",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        // 显示维度标签
        Box(modifier = Modifier.fillMaxSize()) {
            val radius = size.toPx() / 2
            val labelRadius = radius + 24.dp.toPx()
            val centerX = size.toPx() / 2
            val centerY = size.toPx() / 2
            val dimensionCount = dimensionScores.size
            val angleStep = 360f / dimensionCount
            var currentAngle = -90f
            
            dimensionScores.forEach { (name, _) ->
                val labelX = centerX + labelRadius * cos(currentAngle * PI / 180)
                val labelY = centerY + labelRadius * sin(currentAngle * PI / 180)
                
                Box(
                    modifier = Modifier
                        .align(
                            Alignment(0.5f + (labelX - centerX) / size.toPx(),
                                0.5f + (labelY - centerY) / size.toPx())
                        )
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                currentAngle += angleStep
            }
        }
    }
}
