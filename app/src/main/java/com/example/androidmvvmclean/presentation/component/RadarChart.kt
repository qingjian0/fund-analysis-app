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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun RadarChart(
    dimensions: List<String>,
    scores: List<Double>,
    size: Dp = 200.dp
) {
    require(dimensions.size == scores.size) { "Dimensions and scores must have the same size" }
    
    Box(
        modifier = Modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val radius = size.toPx() / 2
            val centerX = size.toPx() / 2
            val centerY = size.toPx() / 2
            val angleStep = 2 * PI / dimensions.size
            
            // 绘制网格
            for (i in 1..5) {
                val gridRadius = radius * (i / 5f)
                val path = Path()
                
                for (j in dimensions.indices) {
                    val angle = -PI / 2 + j * angleStep
                    val x = centerX + gridRadius * cos(angle)
                    val y = centerY + gridRadius * sin(angle)
                    
                    if (j == 0) {
                        path.moveTo(x.toFloat(), y.toFloat())
                    } else {
                        path.lineTo(x.toFloat(), y.toFloat())
                    }
                }
                
                path.close()
                drawPath(
                    path = path,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    style = Stroke(width = 1.dp.toPx())
                )
            }
            
            // 绘制轴线
            dimensions.forEachIndexed { index, _ ->
                val angle = -PI / 2 + index * angleStep
                val x = centerX + radius * cos(angle)
                val y = centerY + radius * sin(angle)
                
                drawLine(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    start = androidx.compose.ui.geometry.Offset(centerX, centerY),
                    end = androidx.compose.ui.geometry.Offset(x.toFloat(), y.toFloat()),
                    strokeWidth = 1.dp.toPx()
                )
            }
            
            // 绘制数据多边形
            val dataPath = Path()
            scores.forEachIndexed { index, score ->
                val angle = -PI / 2 + index * angleStep
                val dataRadius = radius * (score / 100)
                val x = centerX + dataRadius * cos(angle)
                val y = centerY + dataRadius * sin(angle)
                
                if (index == 0) {
                    dataPath.moveTo(x.toFloat(), y.toFloat())
                } else {
                    dataPath.lineTo(x.toFloat(), y.toFloat())
                }
            }
            
            dataPath.close()
            drawPath(
                path = dataPath,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            )
            
            drawPath(
                path = dataPath,
                color = MaterialTheme.colorScheme.primary,
                style = Stroke(width = 2.dp.toPx())
            )
        }
        
        // 显示维度标签
        Box(modifier = Modifier.fillMaxSize()) {
            val radius = size.toPx() / 2
            val labelRadius = radius + 16.dp.toPx()
            val centerX = size.toPx() / 2
            val centerY = size.toPx() / 2
            val angleStep = 2 * PI / dimensions.size
            
            dimensions.forEachIndexed { index, dimension ->
                val angle = -PI / 2 + index * angleStep
                val x = centerX + labelRadius * cos(angle)
                val y = centerY + labelRadius * sin(angle)
                
                Box(
                    modifier = Modifier
                        .align(
                            Alignment(0.5f + (x - centerX) / size.toPx(),
                                0.5f + (y - centerY) / size.toPx())
                        )
                ) {
                    Text(
                        text = dimension,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}
