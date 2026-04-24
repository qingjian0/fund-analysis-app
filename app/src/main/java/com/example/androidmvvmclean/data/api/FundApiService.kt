package com.example.androidmvvmclean.data.api

import com.example.androidmvvmclean.data.model.Fund
import retrofit2.http.GET
import retrofit2.http.Query

interface FundApiService {
    @GET("fund/detail")
    suspend fun getFundDetail(
        @Query("code") fundCode: String
    ): Fund

    @GET("fund/list")
    suspend fun getFundList(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): List<Fund>

    @GET("fund/search")
    suspend fun searchFunds(
        @Query("keyword") keyword: String
    ): List<Fund>
}
