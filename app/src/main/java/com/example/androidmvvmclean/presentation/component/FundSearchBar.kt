package com.example.androidmvvmclean.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun FundSearchBar(
    onSearch: (String) -> Unit, // 搜索回调
    onScan: () -> Unit // 扫码回调
) {
    var searchText by remember { mutableStateOf("") }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text(text = "搜索基金代码") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.weight(1f)
        )
        
        TextButton(
            onClick = { onSearch(searchText) },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(text = "搜索")
        }
        
        TextButton(
            onClick = onScan,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(text = "扫码")
        }
    }
}
