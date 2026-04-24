package com.example.androidmvvmclean.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FilterPanel(
    onApply: (Map<String, Any>) -> Unit // 应用筛选条件回调
) {
    var fundType by remember { mutableStateOf("全部") }
    var style by remember { mutableStateOf("全部") }
    var industry by remember { mutableStateOf("全部") }
    var minScore by remember { mutableStateOf("60") }
    var riskLimit by remember { mutableStateOf("中风险") }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "筛选条件",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Column(modifier = Modifier.padding(top = 32.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "类型：",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    TextButton(onClick = { fundType = "全部" }) {
                        Text(text = "全部")
                    }
                    TextButton(onClick = { fundType = "股票型" }) {
                        Text(text = "股票型")
                    }
                    TextButton(onClick = { fundType = "债券型" }) {
                        Text(text = "债券型")
                    }
                    TextButton(onClick = { fundType = "混合型" }) {
                        Text(text = "混合型")
                    }
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "风格：",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    TextButton(onClick = { style = "全部" }) {
                        Text(text = "全部")
                    }
                    TextButton(onClick = { style = "价值" }) {
                        Text(text = "价值")
                    }
                    TextButton(onClick = { style = "成长" }) {
                        Text(text = "成长")
                    }
                    TextButton(onClick = { style = "平衡" }) {
                        Text(text = "平衡")
                    }
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "行业：",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    TextButton(onClick = { industry = "全部" }) {
                        Text(text = "全部")
                    }
                    TextButton(onClick = { industry = "科技" }) {
                        Text(text = "科技")
                    }
                    TextButton(onClick = { industry = "医疗" }) {
                        Text(text = "医疗")
                    }
                    TextButton(onClick = { industry = "消费" }) {
                        Text(text = "消费")
                    }
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "最低评分：",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    TextButton(onClick = { minScore = "60" }) {
                        Text(text = "60")
                    }
                    TextButton(onClick = { minScore = "70" }) {
                        Text(text = "70")
                    }
                    TextButton(onClick = { minScore = "80" }) {
                        Text(text = "80")
                    }
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "风险上限：",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    TextButton(onClick = { riskLimit = "低风险" }) {
                        Text(text = "低风险")
                    }
                    TextButton(onClick = { riskLimit = "中风险" }) {
                        Text(text = "中风险")
                    }
                    TextButton(onClick = { riskLimit = "高风险" }) {
                        Text(text = "高风险")
                    }
                }
                
                TextButton(
                    onClick = {
                        val filters = mapOf(
                            "fundType" to fundType,
                            "style" to style,
                            "industry" to industry,
                            "minScore" to minScore,
                            "riskLimit" to riskLimit
                        )
                        onApply(filters)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text(text = "应用筛选")
                }
            }
        }
    }
}
