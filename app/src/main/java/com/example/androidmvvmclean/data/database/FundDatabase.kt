package com.example.androidmvvmclean.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidmvvmclean.data.dao.FundDao
import com.example.androidmvvmclean.data.dao.UserPreferencesDao
import com.example.androidmvvmclean.data.model.Fund
import com.example.androidmvvmclean.data.model.UserPreferences

@Database(entities = [Fund::class, UserPreferences::class], version = 2, exportSchema = false)
abstract class FundDatabase : RoomDatabase() {
    abstract fun fundDao(): FundDao
    abstract fun userPreferencesDao(): UserPreferencesDao
}
