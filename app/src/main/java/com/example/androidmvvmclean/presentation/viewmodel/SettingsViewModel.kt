package com.example.androidmvvmclean.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidmvvmclean.data.dao.UserPreferencesDao
import com.example.androidmvvmclean.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val userPreferencesDao: UserPreferencesDao
) : ViewModel() {
    private val _preferences = MutableStateFlow<UserPreferences>(UserPreferences())
    val preferences: StateFlow<UserPreferences> = _preferences

    init {
        loadPreferences()
    }

    private fun loadPreferences() {
        viewModelScope.launch {
            val savedPreferences = userPreferencesDao.getPreferences()
            if (savedPreferences != null) {
                _preferences.value = savedPreferences
            }
        }
    }

    fun updateRiskPreference(preference: RiskPreference) {
        val updated = _preferences.value.copy(riskPreference = preference)
        _preferences.value = updated
        savePreferences(updated)
    }

    fun updateInvestmentHorizon(horizon: InvestmentHorizon) {
        val updated = _preferences.value.copy(investmentHorizon = horizon)
        _preferences.value = updated
        savePreferences(updated)
    }

    fun updateFundSize(size: FundSize) {
        val updated = _preferences.value.copy(fundSize = size)
        _preferences.value = updated
        savePreferences(updated)
    }

    fun updateLlmApiKey(apiKey: String) {
        val updated = _preferences.value.copy(llmApiKey = apiKey)
        _preferences.value = updated
        savePreferences(updated)
    }

    fun updateUseQwen(useQwen: Boolean) {
        val updated = _preferences.value.copy(useQwen = useQwen)
        _preferences.value = updated
        savePreferences(updated)
    }

    private fun savePreferences(preferences: UserPreferences) {
        viewModelScope.launch {
            userPreferencesDao.savePreferences(preferences)
        }
    }

    fun clearCache() {
        // TODO: 实现缓存清理逻辑
        // 这里可以调用相应的Repository方法来清理缓存
    }

    fun getCacheSize(): String {
        // TODO: 实现缓存大小计算逻辑
        return "128MB"
    }
}
