package com.example.androidmvvmclean.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_preferences")
data class UserPreferences(
    @PrimaryKey val id: Int = 1,
    val riskPreference: String = RiskPreference.BALANCED.name,
    val investmentHorizon: String = InvestmentHorizon.MEDIUM.name,
    val fundSize: String = FundSize.MEDIUM.name,
    val llmApiKey: String = "",
    val useQwen: Boolean = true
)

enum class RiskPreference {
    CONSERVATIVE, // 保守型
    BALANCED,     // 平衡型
    AGGRESSIVE    // 激进型
}

enum class InvestmentHorizon {
    SHORT,   // 短期（1年以内）
    MEDIUM,  // 中期（1-3年）
    LONG     // 长期（3年以上）
}

enum class FundSize {
    SMALL,   // 小（<10万）
    MEDIUM,  // 中（10-50万）
    LARGE    // 大（>50万）
}
