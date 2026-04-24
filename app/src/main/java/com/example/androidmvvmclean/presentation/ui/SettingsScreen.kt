package com.example.androidmvvmclean.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.androidmvvmclean.data.model.FundSize
import com.example.androidmvvmclean.data.model.InvestmentHorizon
import com.example.androidmvvmclean.data.model.RiskPreference
import com.example.androidmvvmclean.presentation.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController, viewModel: SettingsViewModel) {
    val preferences = viewModel.preferences.collectAsState().value
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("设置") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("用户偏好设置", style = MaterialTheme.typography.headlineSmall)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            
            // 风险偏好设置
            PreferenceCategory(title = "风险偏好") {
                RadioButtonGroup(
                    options = listOf("保守型", "平衡型", "激进型"),
                    selectedOption = when (preferences.riskPreference) {
                        RiskPreference.CONSERVATIVE -> "保守型"
                        RiskPreference.BALANCED -> "平衡型"
                        RiskPreference.AGGRESSIVE -> "激进型"
                    },
                    onOptionSelected = {
                        val riskPreference = when (it) {
                            "保守型" -> RiskPreference.CONSERVATIVE
                            "平衡型" -> RiskPreference.BALANCED
                            "激进型" -> RiskPreference.AGGRESSIVE
                            else -> RiskPreference.BALANCED
                        }
                        viewModel.updateRiskPreference(riskPreference)
                    }
                )
            }
            
            // 投资期限设置
            PreferenceCategory(title = "投资期限") {
                RadioButtonGroup(
                    options = listOf("短期（1年以内）", "中期（1-3年）", "长期（3年以上）"),
                    selectedOption = when (preferences.investmentHorizon) {
                        InvestmentHorizon.SHORT -> "短期（1年以内）"
                        InvestmentHorizon.MEDIUM -> "中期（1-3年）"
                        InvestmentHorizon.LONG -> "长期（3年以上）"
                    },
                    onOptionSelected = {
                        val horizon = when (it) {
                            "短期（1年以内）" -> InvestmentHorizon.SHORT
                            "中期（1-3年）" -> InvestmentHorizon.MEDIUM
                            "长期（3年以上）" -> InvestmentHorizon.LONG
                            else -> InvestmentHorizon.MEDIUM
                        }
                        viewModel.updateInvestmentHorizon(horizon)
                    }
                )
            }
            
            // 资金规模设置
            PreferenceCategory(title = "资金规模") {
                RadioButtonGroup(
                    options = listOf("小（<10万）", "中（10-50万）", "大（>50万）"),
                    selectedOption = when (preferences.fundSize) {
                        FundSize.SMALL -> "小（<10万）"
                        FundSize.MEDIUM -> "中（10-50万）"
                        FundSize.LARGE -> "大（>50万）"
                    },
                    onOptionSelected = {
                        val size = when (it) {
                            "小（<10万）" -> FundSize.SMALL
                            "中（10-50万）" -> FundSize.MEDIUM
                            "大（>50万）" -> FundSize.LARGE
                            else -> FundSize.MEDIUM
                        }
                        viewModel.updateFundSize(size)
                    }
                )
            }
            
            Divider(modifier = Modifier.padding(vertical = 16.dp))
            
            Text("API配置", style = MaterialTheme.typography.headlineSmall)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            
            // API配置
            PreferenceCategory(title = "API设置") {
                TextField(
                    value = preferences.llmApiKey,
                    onValueChange = { viewModel.updateLlmApiKey(it) },
                    label = { Text("LLM API Key") },
                    placeholder = { Text("请输入API Key") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                SwitchRow(
                    title = "使用通义千问",
                    checked = preferences.useQwen,
                    onCheckedChange = { viewModel.updateUseQwen(it) }
                )
            }
            
            Divider(modifier = Modifier.padding(vertical = 16.dp))
            
            Text("缓存管理", style = MaterialTheme.typography.headlineSmall)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            
            // 缓存管理
            PreferenceCategory(title = "缓存设置") {
                TextRow(
                    title = "缓存大小",
                    value = viewModel.getCacheSize(),
                    onClick = { /* TODO: 显示详细信息 */ }
                )
                
                Button(
                    onClick = { viewModel.clearCache() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("清除缓存")
                }
            }
            
            Divider(modifier = Modifier.padding(vertical = 16.dp))
            
            Text("关于", style = MaterialTheme.typography.headlineSmall)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            
            // 关于
            PreferenceCategory(title = "关于应用") {
                TextRow(
                    title = "版本",
                    value = "1.0.0",
                    onClick = {}
                )
                
                TextRow(
                    title = "免责声明",
                    value = "查看",
                    onClick = {
                        // 显示免责声明对话框
                        androidx.compose.ui.window.Dialog(
                            onDismissRequest = { /* 关闭对话框 */ },
                            properties = androidx.compose.ui.window.DialogProperties()
                        ) {
                            androidx.compose.material3.Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text("免责声明", style = MaterialTheme.typography.headlineMedium)
                                    Divider(modifier = Modifier.padding(vertical = 12.dp))
                                    Text(
                                        "本应用仅供学习研究使用，所有分析结果均基于公开历史数据和数学模型，不构成任何形式的投资建议、投资推荐或投资承诺。",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        "过往业绩不代表未来表现。基金的历史收益率、排名、评级等指标仅反映过去的表现，未来可能发生显著变化。",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                    Text(
                                        "数学模型存在局限性。AHP评分权重具有主观性，贝叶斯预测依赖先验假设，模糊评估存在精度边界，模型输出仅供参考。",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                    Text(
                                        "市场存在不可预测性。宏观经济、政策变化、黑天鹅事件等因素可能导致基金表现大幅偏离预测。",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                    Text(
                                        "投资有风险，决策需谨慎。用户应根据自身的风险承受能力、投资经验和财务状况独立做出投资决策，并对自己的决策承担全部责任。",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                    Text(
                                        "数据可能存在延迟或错误。本应用使用的数据来源于公开渠道，虽已尽力确保准确性，但不对数据的完整性和准确性做出保证。",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                    Text(
                                        "本应用不提供个性化理财服务。如需专业的投资建议，请咨询持牌金融机构或专业投资顾问。",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                    Button(
                                        onClick = { /* 关闭对话框 */ },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 16.dp)
                                    ) {
                                        Text("我知道了")
                                    }
                                }
                            }
                        }
                    }
                )
                
                TextRow(
                    title = "用户使用文档",
                    value = "查看",
                    onClick = {
                        // 显示用户使用文档
                        androidx.compose.ui.window.Dialog(
                            onDismissRequest = { /* 关闭对话框 */ },
                            properties = androidx.compose.ui.window.DialogProperties()
                        ) {
                            androidx.compose.material3.Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text("用户使用文档", style = MaterialTheme.typography.headlineMedium)
                                    Divider(modifier = Modifier.padding(vertical = 12.dp))
                                    Text("1. 基金搜索与分析", style = MaterialTheme.typography.titleMedium)
                                    Text(
                                        "在首页或搜索页面输入基金代码或名称，点击搜索按钮即可找到相关基金。点击基金进入分析页面，查看详细分析结果。",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                    Text("2. 多基金对比", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 12.dp))
                                    Text(
                                        "在对比页面添加2-5只基金，系统会自动生成对比表格、雷达图和关联矩阵，帮助您直观比较基金优劣。",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                    Text("3. 组合配置", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 12.dp))
                                    Text(
                                        "在配置页面设置您的风险偏好、资金规模和投资期限，系统会根据您的偏好生成最优的基金组合配置建议。",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                    Text("4. 市场扫描", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 12.dp))
                                    Text(
                                        "在扫描页面设置筛选条件，系统会扫描全市场基金，为您推荐符合条件的优质基金。",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                    Text("5. 设置", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 12.dp))
                                    Text(
                                        "在设置页面调整您的风险偏好、投资期限和资金规模，配置LLM API密钥，管理缓存数据。",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                    Button(
                                        onClick = { /* 关闭对话框 */ },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 16.dp)
                                    ) {
                                        Text("我知道了")
                                    }
                                }
                            }
                        }
                    }
                )
                
                TextRow(
                    title = "数据来源",
                    value = "查看",
                    onClick = {
                        // 显示数据来源标注
                        androidx.compose.ui.window.Dialog(
                            onDismissRequest = { /* 关闭对话框 */ },
                            properties = androidx.compose.ui.window.DialogProperties()
                        ) {
                            androidx.compose.material3.Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text("数据来源", style = MaterialTheme.typography.headlineMedium)
                                    Divider(modifier = Modifier.padding(vertical = 12.dp))
                                    Text("1. 基金基础信息", style = MaterialTheme.typography.titleMedium)
                                    Text(
                                        "来源：天天基金网 (https://fund.eastmoney.com)",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                    Text("2. 净值数据", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 12.dp))
                                    Text(
                                        "来源：天天基金网 API (https://api.fund.eastmoney.com)",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                    Text("3. 基金搜索", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 12.dp))
                                    Text(
                                        "来源：天天基金网 FundSearch API",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                    Text("4. LLM分析", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 12.dp))
                                    Text(
                                        "来源：OpenAI API / 通义千问 API",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(top = 4.dp)
                                    )
                                    Text(
                                        "免责声明：本应用使用的所有数据均来源于公开渠道，仅供学习研究使用，不保证数据的完整性和准确性。",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(top = 16.dp)
                                    )
                                    Button(
                                        onClick = { /* 关闭对话框 */ },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 16.dp)
                                    ) {
                                        Text("我知道了")
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun PreferenceCategory(title: String, content: @Composable () -> Unit) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(title, style = MaterialTheme.typography.titleMedium)
        content()
    }
}

@Composable
fun RadioButtonGroup(options: List<String>, selectedOption: String, onOptionSelected: (String) -> Unit) {
    Column {
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = { onOptionSelected(option) }
                )
                Text(option, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Composable
fun SwitchRow(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
    ) {
        Text(title)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun TextRow(title: String, value: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
    ) {
        Text(title)
        Text(value, color = MaterialTheme.colorScheme.secondary)
    }
}
