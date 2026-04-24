package com.example.androidmvvmclean.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "funds")
data class Fund(
    @PrimaryKey val fundCode: String,
    val fundName: String,
    val netValue: Double,
    val dayGrowth: Double,
    val weekGrowth: Double,
    val monthGrowth: Double,
    val threeMonthGrowth: Double,
    val sixMonthGrowth: Double,
    val yearGrowth: Double,
    val twoYearGrowth: Double,
    val threeYearGrowth: Double,
    val createTime: Long = System.currentTimeMillis()
)
