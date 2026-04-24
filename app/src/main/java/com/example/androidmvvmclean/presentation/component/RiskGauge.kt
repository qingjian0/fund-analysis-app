package com.example.androidmvvmclean.presentation.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
fun RiskGauge(
    riskValue: Double, // 0-100
    riskLevel: String,
    width: Dp = 240.dp,
    height: Dp = 120.dp
) {
    Box(
        modifier = Modifier
            .width(width)
            .height(height),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerX = width.toPx() / 2
            val centerY = height.toPx()
            val radius = minOf(width.toPx() / 2, height.toPx())
            val strokeWidth = 12.dp.toPx()
            
            // 绘制背景圆弧
            drawArc(
                color = MaterialTheme.colorScheme.surfaceVariant,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round
                )
            )
            
            // 绘制风险等级区域
            // 低风险区域（绿色）
            drawArc(
                color = Color(0xFF4CAF50), // 绿色
                startAngle = 180f,
                sweepAngle = 60f,
                useCenter = false,
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round
                )
            )
            
            // 中风险区域（黄色）
            drawArc(
                color = Color(0xFFFFC107), // 黄色
                startAngle = 240f,
                sweepAngle = 60f,
                useCenter = false,
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round
                )
            )
            
            // 高风险区域（红色）
            drawArc(
                color = Color(0xFFF44336), // 红色
                startAngle = 300f,
                sweepAngle = 60f,
                useCenter = false,
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round
                )
            )
            
            // 计算指针角度
            val angle = 180f + (riskValue / 100) * 180f
            val radians = angle * PI / 180
            val pointerLength = radius - strokeWidth / 2
            val pointerX = centerX + pointerLength * cos(radians)
            val pointerY = centerY - pointerLength * sin(radians)
            
            // 绘制指针
            drawLine(
                color = MaterialTheme.colorScheme.onSurface,
                start = androidx.compose.ui.geometry.Offset(centerX, centerY),
                end = androidx.compose.ui.geometry.Offset(pointerX.toFloat(), pointerY.toFloat()),
                strokeWidth = 3.dp.toPx()
            )
            
            // 绘制指针中心
            drawCircle(
                color = MaterialTheme.colorScheme.onSurface,
                radius = 8.dp.toPx()
            )
        }
        
        // 显示风险等级标签
        Text(
            text = riskLevel,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        // 显示风险值
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            Text(
                text = "风险值: ${riskValue.toInt()}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
