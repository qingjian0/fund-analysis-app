package com.example.androidmvvmclean.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidmvvmclean.data.dao.UserPreferencesDao
import com.example.androidmvvmclean.presentation.viewmodel.SettingsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.multibindings.IntoMap
import javax.inject.Provider

@Module
@InstallIn(ActivityComponent::class)
object ViewModelModule {

    @Provides
    @ActivityScoped
    fun provideSettingsViewModel(
        userPreferencesDao: UserPreferencesDao
    ): SettingsViewModel {
        return SettingsViewModel(userPreferencesDao)
    }

    @Provides
    fun provideSettingsViewModelFactory(
        settingsViewModel: Provider<SettingsViewModel>
    ): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
                    return settingsViewModel.get() as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}
