package com.example.androidmvvmclean.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.androidmvvmclean.presentation.component.*
import kotlinx.coroutines.delay

@Composable
fun AnalyzeScreen(navController: NavHostController, fundCode: String) {
    var isLoading by remember { mutableStateOf(true) }
    var fundInfo by remember { mutableStateOf<FundInfo?>(null) }
    var analysisResult by remember { mutableStateOf<AnalysisResult?>(null) }

    // 模拟数据加载
    LaunchedEffect(fundCode) {
        isLoading = true
        // 模拟网络请求延迟
        kotlinx.coroutines.delay(1000)
        fundInfo = FundInfo(
            code = fundCode,
            name = "易方达消费行业股票",
            type = "股票型",
            scale = "125.6亿",
            manager = "萧楠",
            managerTenure = "8.5年",
            company = "易方达基金"
        )
        // 模拟分析计算延迟
        kotlinx.coroutines.delay(1500)
        analysisResult = AnalysisResult(
            score = 85,
            dimensionScores = mapOf(
                "收益" to 90,
                "风险" to 75,
                "经理" to 95,
                "公司" to 90,
                "环境" to 75
            ),
            riskLevel = "中低风险",
            bayesPrediction = BayesPrediction(0.65, 0.25, 0.10),
            contradiction = "当前主要矛盾是消费行业估值偏高与基本面改善的矛盾",
            agents = listOf(
                AgentReport("价值", 80, "估值合理，业绩稳健"),
                AgentReport("成长", 85, "行业龙头，成长潜力大"),
                AgentReport("量化", 78, "因子表现良好"),
                AgentReport("宏观", 82, "消费升级趋势明显")
            ),
            advice = "建议持有，建议仓位60%，止盈目标15%，止损线-8%"
        )
        isLoading = false
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text("正在分析基金...")
            }
        }
    } else {
        fundInfo?.let { info ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // 基金基本信息
                FundInfoCard(fundInfo = info)
                Spacer(modifier = Modifier.height(24.dp))

                // 综合评分卡
                analysisResult?.let { result ->
                    ScoreCard(score = result.score)
                    Spacer(modifier = Modifier.height(24.dp))

                    // 五维雷达图
                    RadarChart(dimensionScores = result.dimensionScores)
                    Spacer(modifier = Modifier.height(24.dp))

                    // 风险仪表盘
                    RiskGauge(riskLevel = result.riskLevel)
                    Spacer(modifier = Modifier.height(24.dp))

                    // 贝叶斯预测条
                    BayesBar(prediction = result.bayesPrediction)
                    Spacer(modifier = Modifier.height(24.dp))

                    // 核心矛盾分析
                    ContradictionCard(contradiction = result.contradiction)
                    Spacer(modifier = Modifier.height(24.dp))

                    // 四Agent分析报告
                    AgentReportCard(agents = result.agents)
                    Spacer(modifier = Modifier.height(24.dp))

                    // 投资建议
                    AdviceCard(advice = result.advice)
                    Spacer(modifier = Modifier.height(24.dp))

                    // 历史净值走势图
                    NavTrendChart()
                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
fun FundInfoCard(fundInfo: FundInfo) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = fundInfo.name,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "代码: ${fundInfo.code}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "类型: ${fundInfo.type}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "规模: ${fundInfo.scale}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "公司: ${fundInfo.company}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "经理: ${fundInfo.manager}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "任职: ${fundInfo.managerTenure}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

data class FundInfo(
    val code: String,
    val name: String,
    val type: String,
    val scale: String,
    val manager: String,
    val managerTenure: String,
    val company: String
)

data class AnalysisResult(
    val score: Int,
    val dimensionScores: Map<String, Int>,
    val riskLevel: String,
    val bayesPrediction: BayesPrediction,
    val contradiction: String,
    val agents: List<AgentReport>,
    val advice: String
)

data class BayesPrediction(
    val upProbability: Double,
    val flatProbability: Double,
    val downProbability: Double
)

data class AgentReport(
    val name: String,
    val score: Int,
    val opinion: String
)