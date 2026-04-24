package com.example.androidmvvmclean.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.androidmvvmclean.presentation.component.RadarChart
import com.example.androidmvvmclean.presentation.navigation.Screen

// 模拟基金数据
val mockFunds = listOf(
    FundCompareData("005827", "易方达蓝筹精选", 78.0, 18.5, 1.42, -22.3, mapOf("收益" to 85.0, "风险" to 65.0, "经理" to 90.0, "公司" to 80.0, "环境" to 70.0)),
    FundCompareData("110011", "易方达优质精选", 72.0, 15.2, 1.28, -18.5, mapOf("收益" to 75.0, "风险" to 70.0, "经理" to 85.0, "公司" to 80.0, "环境" to 65.0)),
    FundCompareData("161725", "招商中证白酒", 65.0, 12.8, 0.95, -28.1, mapOf("收益" to 70.0, "风险" to 60.0, "经理" to 75.0, "公司" to 70.0, "环境" to 60.0))
)

// 基金对比数据类
data class FundCompareData(
    val code: String,
    val name: String,
    val totalScore: Double,
    val annualReturn: Double,
    val sharpeRatio: Double,
    val maxDrawdown: Double,
    val dimensionScores: Map<String, Double>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompareScreen(navController: NavHostController) {
    var funds by remember { mutableStateOf(mockFunds) }
    var searchText by remember { mutableStateOf("" ) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("多基金对比") },
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
            // 添加基金部分
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "添加基金（最多5只）",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        funds.forEach { fund ->
                            Chip(
                                onClick = { /* 移除基金 */ },
                                label = { Text("${fund.code}") },
                                trailingIcon = {
                                    IconButton(onClick = { 
                                        funds = funds.filter { it.code != fund.code }
                                    }) {
                                        androidx.compose.material3.Icon(
                                            imageVector = androidx.compose.material.icons.Icons.Default.Close,
                                            contentDescription = "移除",
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            )
                        }
                        if (funds.size < 5) {
                            OutlinedButton(
                                onClick = { navController.navigate(Screen.Search.route) },
                                modifier = Modifier.padding(0.dp)
                            ) {
                                Text("+ 添加")
                            }
                        }
                    }
                }
            }
            
            // 对比表格
            if (funds.isNotEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "指标对比",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            
                            // 表头
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("指标", fontWeight = FontWeight.Bold)
                                funds.forEach {
                                    Text(it.name, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                                }
                            }
                            
                            // 分隔线
                            Divider(modifier = Modifier.padding(vertical = 8.dp))
                            
                            // 数据行
                            ComparisonRow("综合评分", funds.map { it.totalScore.toString() })
                            ComparisonRow("年化收益", funds.map { "${it.annualReturn}%" })
                            ComparisonRow("夏普比率", funds.map { it.sharpeRatio.toString() })
                            ComparisonRow("最大回撤", funds.map { "${it.maxDrawdown}%" })
                        }
                    }
                }
                
                // 雷达图对比
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "雷达图叠加对比",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            RadarChart(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp),
                                data = funds.map { it.dimensionScores },
                                labels = funds.map { it.name },
                                colors = listOf(
                                    Color(0xFF2196F3),
                                    Color(0xFF4CAF50),
                                    Color(0xFFFF9800)
                                )
                            )
                        }
                    }
                }
                
                // 资产关联矩阵热力图
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "资产关联矩阵热力图",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            // 简化版热力图
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .background(Color.LightGray)
                            ) {
                                Text(
                                    text = "关联矩阵热力图",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                }
                
                // 排名结果
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "排名结果",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            val sortedFunds = funds.sortedByDescending { it.totalScore }
                            Text(
                                text = "🏆 排名：${sortedFunds.joinToString(" > ") { it.name }}",
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ComparisonRow(label: String, values: List<String>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, modifier = Modifier.weight(1f))
        values.forEach {
            Text(it, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
        }
    }
    Divider(modifier = Modifier.padding(vertical = 4.dp))
}
