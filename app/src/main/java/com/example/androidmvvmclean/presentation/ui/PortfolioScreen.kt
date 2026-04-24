package com.example.androidmvvmclean.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

// 风险偏好类型
enum class RiskPreference {
    CONSERVATIVE, BALANCED, AGGRESSIVE
}

// 投资期限
enum class InvestmentHorizon {
    ONE_YEAR, THREE_YEARS, FIVE_YEARS
}

// 组合配置数据
data class PortfolioConfig(
    val fundCode: String,
    val fundName: String,
    val allocation: Double,
    val amount: Double,
    val type: String // 核心/卫星/底仓
)

// 模拟组合配置数据
val mockPortfolioConfig = listOf(
    PortfolioConfig("005827", "易方达蓝筹精选", 25.0, 25000.0, "核心"),
    PortfolioConfig("003095", "中欧医疗健康", 15.0, 15000.0, "卫星"),
    PortfolioConfig("161725", "招商中证白酒", 20.0, 20000.0, "核心"),
    PortfolioConfig("001511", "兴全合润混合", 10.0, 10000.0, "卫星"),
    PortfolioConfig("000209", "信诚新兴产业混合", 10.0, 10000.0, "卫星"),
    PortfolioConfig("001391", "中加纯债债券", 20.0, 20000.0, "底仓")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioScreen(navController: NavHostController) {
    var riskPreference by remember { mutableStateOf(RiskPreference.BALANCED) }
    var investmentHorizon by remember { mutableStateOf(InvestmentHorizon.FIVE_YEARS) }
    var investmentAmount by remember { mutableStateOf(100000.0) }
    var portfolioConfig by remember { mutableStateOf(mockPortfolioConfig) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("组合配置") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        androidx.compose.material3.Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                            contentDescription = "返回"
                        )
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 风险偏好选择
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "风险偏好",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            RiskPreference.values().forEach { preference ->
                                val isSelected = riskPreference == preference
                                OutlinedButton(
                                    onClick = { riskPreference = preference },
                                    modifier = Modifier.weight(1f),
                                    border = if (isSelected) {
                                        ButtonDefaults.outlinedButtonBorder.copy(
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    } else null,
                                    colors = if (isSelected) {
                                        ButtonDefaults.outlinedButtonColors(
                                            containerColor = MaterialTheme.colorScheme.primaryContainer
                                        )
                                    } else ButtonDefaults.outlinedButtonColors()
                                ) {
                                    Text(
                                        when (preference) {
                                            RiskPreference.CONSERVATIVE -> "🛡 保守"
                                            RiskPreference.BALANCED -> "⚖ 平衡"
                                            RiskPreference.AGGRESSIVE -> "⚔ 激进"
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            // 投资参数设置
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "投资参数",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        
                        // 资金规模
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("资金规模：")
                            Text("¥${investmentAmount.toInt()}")
                            IconButton(onClick = { 
                                // 这里可以添加金额调整逻辑
                            }) {
                                androidx.compose.material3.Icon(
                                    imageVector = androidx.compose.material.icons.Icons.Default.Edit,
                                    contentDescription = "编辑"
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        // 投资期限
                        Text("投资期限：")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            InvestmentHorizon.values().forEach { horizon ->
                                val isSelected = investmentHorizon == horizon
                                OutlinedButton(
                                    onClick = { investmentHorizon = horizon },
                                    modifier = Modifier.weight(1f),
                                    border = if (isSelected) {
                                        ButtonDefaults.outlinedButtonBorder.copy(
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    } else null,
                                    colors = if (isSelected) {
                                        ButtonDefaults.outlinedButtonColors(
                                            containerColor = MaterialTheme.colorScheme.primaryContainer
                                        )
                                    } else ButtonDefaults.outlinedButtonColors()
                                ) {
                                    Text(
                                        when (horizon) {
                                            InvestmentHorizon.ONE_YEAR -> "1年"
                                            InvestmentHorizon.THREE_YEARS -> "3年"
                                            InvestmentHorizon.FIVE_YEARS -> "5年"
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
            
            // 配置饼图
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "配置比例",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        // 简化版饼图
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .background(Color.LightGray)
                        ) {
                            Text(
                                text = "配置饼图",
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            PieLegendItem("核心持仓 60%", Color(0xFF2196F3))
                            PieLegendItem("卫星持仓 30%", Color(0xFF4CAF50))
                            PieLegendItem("现金储备 10%", Color(0xFFFF9800))
                        }
                    }
                }
            }
            
            // 推荐配置
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "推荐配置",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        portfolioConfig.forEach { config ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(config.fundName, fontWeight = FontWeight.Medium)
                                    Text("${config.fundCode}", fontSize = 12.sp, color = Color.Gray)
                                }
                                Column(
                                    horizontalAlignment = androidx.compose.ui.Alignment.End
                                ) {
                                    Text("${config.allocation}%", fontWeight = FontWeight.Bold)
                                    Text("¥${config.amount.toInt()}", fontSize = 12.sp)
                                    Text(config.type, fontSize = 12.sp, color = Color.Gray)
                                }
                            }
                            Divider(modifier = Modifier.padding(vertical = 8.dp))
                        }
                    }
                }
            }
            
            // 组合预期表现
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "组合预期表现",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            PerformanceItem("年化收益", "8-12%")
                            PerformanceItem("波动率", "10-15%")
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            PerformanceItem("夏普比率", "1.1")
                            PerformanceItem("最大回撤", "-15%")
                        }
                    }
                }
            }
            
            // 调仓建议
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "调仓建议",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        Text("• 集中兵力：科技方向确定性最高")
                        Text("• 减持：债券比例过高，建议降至20%")
                        Text("• 新增：可考虑新能源主题基金")
                    }
                }
            }
        }
    }
}

@Composable
fun PieLegendItem(label: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color)
        )
        Text(label, fontSize = 12.sp)
    }
}

@Composable
fun PerformanceItem(label: String, value: String) {
    Column(
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(label, fontSize = 12.sp, color = Color.Gray)
        Text(value, fontWeight = FontWeight.Bold)
    }
}
