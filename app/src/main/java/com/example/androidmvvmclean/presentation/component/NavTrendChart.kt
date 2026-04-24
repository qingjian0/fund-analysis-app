package com.example.androidmvvmclean.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.style.ChartStyle
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.entry.entriesOf

@Composable
fun NavTrendChart(
    navData: List<Pair<String, Double>>, // 日期和净值
    height: Dp = 200.dp
) {
    val entries = navData.map { it.second }
    val entryModel = entryModelOf(*entries.toTypedArray())
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
    ) {
        ProvideChartStyle(
            ChartStyle(
                chartColors = listOf(MaterialTheme.colorScheme.primary)
            )
        ) {
            Chart(
                chart = lineChart(),
                model = entryModel,
                startAxis = rememberStartAxis(),
                bottomAxis = rememberBottomAxis()
            )
        }
    }
}
