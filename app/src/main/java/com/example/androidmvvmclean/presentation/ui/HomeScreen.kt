package com.example.androidmvvmclean.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.androidmvvmclean.presentation.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(navController: NavHostController) {
    var marketOverview by remember { mutableStateOf<MarketOverview?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // 模拟市场概览数据加载
    LaunchedEffect(Unit) {
        isLoading = true
        // 模拟网络请求延迟
        kotlinx.coroutines.delay(800)
        marketOverview = MarketOverview(
            marketIndex = 3258.63,
            change = 1.25,
            changePercent = 0.04,
            hotFunds = listOf(
                HotFund("110022", "易方达消费行业股票", 5.23),
                HotFund("161725", "招商中证白酒指数", 3.85),
                HotFund("001511", "兴全合润混合", 2.67),
                HotFund("000001", "华夏成长混合", 1.98)
            )
        )
        isLoading = false
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        // 顶部标题
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "求是基金",
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = "智能基金分析助手",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            marketOverview?.let { overview ->
                // 市场概览卡片
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    ) {
                        Text(
                            text = "市场概览",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "${overview.marketIndex}",
                                    style = MaterialTheme.typography.displayMedium
                                )
                                Text(
                                    text = "上证指数",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Column(
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = "${if (overview.change >= 0) "+" else ""}${overview.change} (${overview.changePercent * 100}%)",
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = if (overview.change >= 0) 
                                        MaterialTheme.colorScheme.primary 
                                    else 
                                        MaterialTheme.colorScheme.error
                                )
                                Text(
                                    text = "今日",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }

                // 热门基金
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "热门基金",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(overview.hotFunds) {
                            HotFundCard(fund = it) {
                                navController.navigate(Screen.Analyze.createRoute(it.code))
                            }
                        }
                    }
                }
            }
        }

        // 快捷入口
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "快捷功能",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            Grid(
                modifier = Modifier.fillMaxWidth(),
                rows = 2,
                columns = 3,
                spacing = 12.dp
            ) {
                QuickActionItem(
                    title = "基金搜索",
                    icon = "🔍"
                ) {
                    navController.navigate(Screen.Search.route)
                }
                QuickActionItem(
                    title = "基金分析",
                    icon = "📊"
                ) {
                    // 跳转到搜索页，让用户输入基金代码
                    navController.navigate(Screen.Search.route)
                }
                QuickActionItem(
                    title = "多基金对比",
                    icon = "⚖️"
                ) {
                    navController.navigate(Screen.Compare.route)
                }
                QuickActionItem(
                    title = "组合配置",
                    icon = "🎯"
                ) {
                    navController.navigate(Screen.Portfolio.route)
                }
                QuickActionItem(
                    title = "市场扫描",
                    icon = "🔎"
                ) {
                    navController.navigate(Screen.Scan.route)
                }
                QuickActionItem(
                    title = "设置",
                    icon = "⚙️"
                ) {
                    navController.navigate(Screen.Settings.route)
                }
            }
        }
    }
}

@Composable
fun HotFundCard(fund: HotFund, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(100.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = fund.name,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2
            )
            Text(
                text = "${fund.dailyReturn}%",
                style = MaterialTheme.typography.headlineMedium,
                color = if (fund.dailyReturn >= 0) 
                    MaterialTheme.colorScheme.primary 
                else 
                    MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun QuickActionItem(title: String, icon: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = icon,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun Grid(
    modifier: Modifier = Modifier,
    rows: Int,
    columns: Int,
    spacing: Dp,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(spacing)
    ) {
        repeat(rows) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(spacing)
            ) {
                repeat(columns) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                    ) {
                        content()
                    }
                }
            }
        }
    }
}

data class MarketOverview(
    val marketIndex: Double,
    val change: Double,
    val changePercent: Double,
    val hotFunds: List<HotFund>
)

data class HotFund(
    val code: String,
    val name: String,
    val dailyReturn: Double
)
