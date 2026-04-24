package com.example.androidmvvmclean.data.di

import android.content.Context
import androidx.room.Room
import com.example.androidmvvmclean.data.api.FundApiService
import com.example.androidmvvmclean.data.database.FundDatabase
import com.example.androidmvvmclean.data.repository.FundRepository
import com.example.androidmvvmclean.data.repository.UserRepository
import com.example.androidmvvmclean.data.repository.impl.FundRepositoryImpl
import com.example.androidmvvmclean.data.repository.impl.UserRepositoryImpl
import com.example.androidmvvmclean.domain.usecase.GetUserByIdUseCase
import com.example.androidmvvmclean.domain.usecase.GetUsersUseCase
import com.squareup.okhttp3.OkHttpClient
import com.squareup.okhttp3.logging.HttpLoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository {
        return UserRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideGetUsersUseCase(userRepository: UserRepository): GetUsersUseCase {
        return GetUsersUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideGetUserByIdUseCase(userRepository: UserRepository): GetUserByIdUseCase {
        return GetUserByIdUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideFundApiService(okHttpClient: OkHttpClient): FundApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.tiantianfund.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FundApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideFundDatabase(@ApplicationContext context: Context): FundDatabase {
        return Room.databaseBuilder(
            context,
            FundDatabase::class.java,
            "fund_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFundDao(fundDatabase: FundDatabase) = fundDatabase.fundDao()

    @Provides
    @Singleton
    fun provideUserPreferencesDao(fundDatabase: FundDatabase) = fundDatabase.userPreferencesDao()

    @Provides
    @Singleton
    fun provideFundRepository(
        apiService: FundApiService,
        fundDao: com.example.androidmvvmclean.data.dao.FundDao
    ): FundRepository {
        return FundRepositoryImpl(apiService, fundDao)
    }

    @Provides
    @Singleton
    fun provideGetFundDetailUseCase(fundRepository: FundRepository): com.example.androidmvvmclean.domain.usecase.GetFundDetailUseCase {
        return com.example.androidmvvmclean.domain.usecase.GetFundDetailUseCase(fundRepository)
    }

    @Provides
    @Singleton
    fun provideGetFundListUseCase(fundRepository: FundRepository): com.example.androidmvvmclean.domain.usecase.GetFundListUseCase {
        return com.example.androidmvvmclean.domain.usecase.GetFundListUseCase(fundRepository)
    }

    @Provides
    @Singleton
    fun provideGetFundSearchUseCase(fundRepository: FundRepository): com.example.androidmvvmclean.domain.usecase.GetFundSearchUseCase {
        return com.example.androidmvvmclean.domain.usecase.GetFundSearchUseCase(fundRepository)
    }
}
