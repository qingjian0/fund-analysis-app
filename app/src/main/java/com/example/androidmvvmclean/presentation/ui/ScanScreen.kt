package com.example.androidmvvmclean.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

// 筛选条件数据类
data class ScanFilter(
    var fundType: String = "全部",
    var style: String = "全部",
    var industry: String = "全部",
    var minScore: Int = 60,
    var riskLimit: String = "中风险"
)

// 扫描结果数据类
data class ScanResult(
    val rank: Int,
    val code: String,
    val name: String,
    val score: Int,
    val risk: String,
    val upProbability: Int,
    val reason: String
)

// 模拟扫描结果数据
val mockScanResults = listOf(
    ScanResult(1, "005827", "易方达蓝筹精选", 82, "中风险", 62, "AHP评分Top3，夏普>1.4"),
    ScanResult(2, "003095", "中欧医疗健康", 78, "中风险", 58, "行业景气度高，成长性强"),
    ScanResult(3, "161725", "招商中证白酒", 75, "中风险", 55, "行业龙头，业绩稳定"),
    ScanResult(4, "001511", "兴全合润混合", 73, "中风险", 53, "基金经理能力强，长期业绩优秀"),
    ScanResult(5, "000209", "信诚新兴产业混合", 71, "中风险", 51, "新兴产业布局，成长空间大"),
    ScanResult(6, "001391", "中加纯债债券", 69, "低风险", 45, "债券配置优选，收益稳定"),
    ScanResult(7, "001475", "易方达国防军工", 68, "高风险", 56, "军工行业景气，弹性大"),
    ScanResult(8, "001616", "嘉实环保低碳", 67, "中风险", 52, "环保行业政策支持，成长潜力大"),
    ScanResult(9, "001714", "工银瑞信文体产业", 66, "中风险", 50, "文体产业消费升级，长期看好"),
    ScanResult(10, "001811", "中欧明睿新常态", 65, "中风险", 48, "均衡配置，风险可控")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanScreen(navController: NavHostController) {
    var filter by remember { mutableStateOf(ScanFilter()) }
    var scanResults by remember { mutableStateOf(mockScanResults) }
    var isScanning by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("市场扫描") },
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
            // 筛选条件
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "筛选条件",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("类型：", fontSize = 14.sp)
                                Text(filter.fundType, fontWeight = FontWeight.Medium)
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text("风格：", fontSize = 14.sp)
                                Text(filter.style, fontWeight = FontWeight.Medium)
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("行业：", fontSize = 14.sp)
                                Text(filter.industry, fontWeight = FontWeight.Medium)
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text("最低评分：", fontSize = 14.sp)
                                Text(filter.minScore.toString(), fontWeight = FontWeight.Medium)
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("风险上限：", fontSize = 14.sp)
                            Text(filter.riskLimit, fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 8.dp))
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Button(
                            onClick = {
                                // 模拟扫描过程
                                isScanning = true
                                // 这里可以添加实际的扫描逻辑
                                isScanning = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(if (isScanning) "扫描中..." else "开始扫描")
                        }
                    }
                }
            }
            
            // 筛选漏斗
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "筛选漏斗",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            FunnelStep("全市场", "8000+")
                            FunnelStep("初筛", "1200")
                            FunnelStep("AHP评分", "350")
                            FunnelStep("风险过滤", "180")
                            FunnelStep("贝叶斯筛选", "85")
                            FunnelStep("最终", "Top10")
                        }
                    }
                }
            }
            
            // 扫描结果
            item {
                Text(
                    text = "扫描结果",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            items(scanResults) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = when (it.rank) {
                                    1 -> "🥇 ${it.name} ${it.code}"
                                    2 -> "🥈 ${it.name} ${it.code}"
                                    3 -> "🥉 ${it.name} ${it.code}"
                                    else -> "${it.rank}. ${it.name} ${it.code}"
                                },
                                fontWeight = FontWeight.Bold
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("评分 ${it.score}")
                            Text("${it.risk}")
                            Text("上涨 ${it.upProbability}%")
                        }
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "入选理由：${it.reason}",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FunnelStep(label: String, count: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(label, fontSize = 12.sp)
        Text(count, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}
