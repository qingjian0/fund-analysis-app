package com.example.androidmvvmclean.data.repository

import com.example.androidmvvmclean.data.model.Fund

interface FundRepository {
    suspend fun getFundDetail(fundCode: String): Fund
    suspend fun getFundList(page: Int, size: Int): List<Fund>
    suspend fun searchFunds(keyword: String): List<Fund>
    suspend fun getCachedFund(fundCode: String): Fund?
}
