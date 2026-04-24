package com.example.androidmvvmclean.domain.usecase

import com.example.androidmvvmclean.data.model.Fund
import com.example.androidmvvmclean.data.repository.FundRepository

class GetFundDetailUseCase(
    private val fundRepository: FundRepository
) {
    suspend operator fun invoke(fundCode: String): Fund {
        return fundRepository.getFundDetail(fundCode)
    }
}
