package com.example.androidmvvmclean.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidmvvmclean.data.model.UserPreferences

@Dao
interface UserPreferencesDao {
    @Query("SELECT * FROM user_preferences LIMIT 1")
    suspend fun getPreferences(): UserPreferences?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePreferences(preferences: UserPreferences)

    @Query("DELETE FROM user_preferences")
    suspend fun clearPreferences()
}
