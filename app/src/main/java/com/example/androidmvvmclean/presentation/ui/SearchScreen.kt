package com.example.androidmvvmclean.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.androidmvvmclean.presentation.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SearchScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var searchResults by remember { mutableStateOf(listOf<FundSearchResult>()) }
    var isLoading by remember { mutableStateOf(false) }

    // 模拟搜索结果
    val mockFunds = listOf(
        FundSearchResult("110022", "易方达消费行业股票", "股票型"),
        FundSearchResult("161725", "招商中证白酒指数(LOF)", "指数型"),
        FundSearchResult("001511", "兴全合润混合", "混合型"),
        FundSearchResult("000001", "华夏成长混合", "混合型"),
        FundSearchResult("110011", "易方达优质精选混合(QDII)", "QDII")
    )

    LaunchedEffect(searchQuery.text) {
        if (searchQuery.text.isNotEmpty()) {
            isLoading = true
            // 模拟网络请求延迟
            kotlinx.coroutines.delay(500)
            searchResults = mockFunds.filter { 
                it.code.contains(searchQuery.text) || 
                it.name.contains(searchQuery.text)
            }
            isLoading = false
        } else {
            searchResults = emptyList()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 搜索栏
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("输入基金代码或名称") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true,
            trailingIcon = { Icon(Icons.Default.Search, contentDescription = "搜索") }
        )

        // 搜索结果
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (searchResults.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(searchResults) {
                    FundSearchItem(fund = it) {
                        navController.navigate(Screen.Analyze.createRoute(it.code))
                    }
                }
            }
        } else if (searchQuery.text.isNotEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("未找到匹配的基金")
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("请输入基金代码或名称进行搜索")
            }
        }
    }
}

@Composable
fun FundSearchItem(fund: FundSearchResult, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = fund.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = fund.code,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = fund.type,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

data class FundSearchResult(
    val code: String,
    val name: String,
    val type: String
)