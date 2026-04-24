package com.example.androidmvvmclean.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidmvvmclean.data.model.Fund

@Dao
interface FundDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFund(fund: Fund)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFunds(funds: List<Fund>)

    @Query("SELECT * FROM funds WHERE fundCode = :fundCode")
    suspend fun getFundByCode(fundCode: String): Fund?

    @Query("SELECT * FROM funds ORDER BY createTime DESC LIMIT :limit OFFSET :offset")
    suspend fun getFunds(limit: Int, offset: Int): List<Fund>

    @Query("DELETE FROM funds WHERE createTime < :timeThreshold")
    suspend fun deleteOldFunds(timeThreshold: Long)

    @Query("DELETE FROM funds")
    suspend fun clearFunds()
}
