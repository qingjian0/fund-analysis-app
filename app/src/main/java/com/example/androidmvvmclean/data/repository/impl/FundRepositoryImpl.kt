package com.example.androidmvvmclean.data.repository.impl

import com.example.androidmvvmclean.data.api.FundApiService
import com.example.androidmvvmclean.data.dao.FundDao
import com.example.androidmvvmclean.data.model.Fund
import com.example.androidmvvmclean.data.repository.FundRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class FundRepositoryImpl(
    private val apiService: FundApiService,
    private val fundDao: FundDao
) : FundRepository {

    override suspend fun getFundDetail(fundCode: String): Fund {
        return withContext(Dispatchers.IO) {
            try {
                // 尝试从网络获取数据
                val fund = apiService.getFundDetail(fundCode)
                // 缓存到本地数据库
                fundDao.insertFund(fund)
                fund
            } catch (e: HttpException) {
                // 网络错误，尝试从缓存获取
                val cachedFund = fundDao.getFundByCode(fundCode)
                cachedFund ?: throw IOException("网络错误且无缓存数据")
            } catch (e: IOException) {
                // 网络异常，尝试从缓存获取
                val cachedFund = fundDao.getFundByCode(fundCode)
                cachedFund ?: throw e
            }
        }
    }

    override suspend fun getFundList(page: Int, size: Int): List<Fund> {
        return withContext(Dispatchers.IO) {
            try {
                // 尝试从网络获取数据
                val funds = apiService.getFundList(page, size)
                // 缓存到本地数据库
                fundDao.insertFunds(funds)
                funds
            } catch (e: HttpException) {
                // 网络错误，从缓存获取
                val offset = (page - 1) * size
                fundDao.getFunds(size, offset)
            } catch (e: IOException) {
                // 网络异常，从缓存获取
                val offset = (page - 1) * size
                fundDao.getFunds(size, offset)
            }
        }
    }

    override suspend fun searchFunds(keyword: String): List<Fund> {
        return withContext(Dispatchers.IO) {
            try {
                // 尝试从网络搜索
                apiService.searchFunds(keyword)
            } catch (e: Exception) {
                // 网络错误，返回空列表
                emptyList()
            }
        }
    }

    override suspend fun getCachedFund(fundCode: String): Fund? {
        return withContext(Dispatchers.IO) {
            fundDao.getFundByCode(fundCode)
        }
    }
}
